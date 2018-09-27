import * as React from 'react';
import { Input, Radio, Select, DatePicker, InputNumber } from 'antd';
import moment from 'moment';
import merger from '../util/merger';
import { service } from '../service';
import { Page } from './page';
import { Image, getImageValue } from './ui/image';

export interface Meta {
    props: MetaProp[];
}

export interface MetaProp {
    name: string;
    label?: string;
    type?: string;
    labels?: string[];
    values?: object;
    upload?: string;
    ignore?: string[];
}

export interface PageMeta {
    type: string;
    service?: string;
    parameter?: object;
    search?: MetaProp[];
    toolbar?: Action[];
    ops?: Action[];
}

export interface Action {
    type: string;
    service?: string;
    icon?: string;
    label?: string;
    when?: string;
    parameter?: {};
    success?: {
        service: string,
        header?: {},
        parameter?: {}
    };
}

export interface Model {
    id?: string;
}

const icons = {
    'search': 'search',
    'create': 'plus',
    'modify': 'edit',
    'save': 'save',
    'copy': 'copy',
    'delete': 'delete'
};

const RadioGroup = Radio.Group;
const Option = Select.Option;
const TextArea = Input.TextArea;
const DateFormat = 'YYYY-MM-DD';

class Pager {
    private page: Page;
    private metas: { [key: string]: Meta } = {};
    private key: string;

    public bind(page: Page): void {
        this.page = page;
    }

    public to(key: string, header?: {}, parameter?: {}): void {
        this.getMeta(key).then(meta => {
            if (meta === null) {
                return;
            }

            const pageMeta = meta[key.substring(key.lastIndexOf('.') + 1)];
            let postKey = key;
            if (pageMeta.service) {
                postKey = key.substring(0, key.lastIndexOf('.') + 1) + pageMeta.service;
            }
            this.post(postKey, header, parameter).then(data => {
                this.setPage(key, meta, pageMeta, data, header, parameter);
            });
        });
    }

    public getMeta(key: string): Promise<Meta> {
        this.key = key.substring(0, key.lastIndexOf('.'));
        if (this.metas.hasOwnProperty(this.key)) {
            return new Promise((resolve, reject) => {
                resolve(this.metas[this.key]);
            });
        }

        return service.post('/ui/console/meta', { key: this.key }, {}).then(data => {
            if (data === null) {
                return null;
            }

            this.metas[this.key] = data;

            return data;
        });
    }

    public setPage(key: string, meta: Meta, pageMeta: PageMeta, data: any, header?: {}, parameter?: {}): void {
        this.toolbar(pageMeta.toolbar);
        this.page.setState({ service: 'blank' }, () => {
            this.page.setState({
                service: key,
                header: header,
                parameter: parameter,
                meta: pageMeta,
                props: this.ignoreProps(key, meta.props),
                data: data
            });
        });
    }

    private ignoreProps(key: string, props: MetaProp[]): MetaProp[] {
        const array: MetaProp[] = [];
        const suffix = key.substring(key.lastIndexOf('.') + 1);
        for (const prop of props) {
            if (!this.ignorable(prop, suffix)) {
                array.push(prop);
            }
        }

        return array;
    }

    private ignorable(prop: MetaProp, suffix: string): boolean {
        if (!prop.ignore || prop.ignore.length === 0) {
            return false;
        }

        for (const i of prop.ignore) {
            if (i === suffix) {
                return true;
            }
        }

        return false;
    }

    private toolbar(toolbar?: Action[]): void {
        if (!toolbar) {
            return;
        }

        for (const button of toolbar) {
            button.icon = this.getButtonIcon(button.type, button.icon);
        }
    }

    private getButtonIcon(type: string, icon?: string): string | undefined {
        if (icon) {
            return icon;
        }

        for (const key in icons) {
            if (icons.hasOwnProperty(key) && type.endsWith(key)) {
                return icons[key];
            }
        }

        return undefined;
    }

    public getInput(form: any, prop: MetaProp, data?: {}): JSX.Element {
        const { getFieldDecorator } = form;
        const config = {
            initialValue: this.getModelValue(data, prop.name)
        };
        if (prop.type === 'date') {
            if (config.initialValue === '') {
                delete config.initialValue;
            } else {
                config.initialValue = moment(config.initialValue, DateFormat);
            }
        } else if (prop.type === 'money') {
            config.initialValue = (config.initialValue * 0.01).toFixed(2);
        }

        return getFieldDecorator(prop.name, config)(this.getInputElement(prop));
    }

    private getInputElement(prop: MetaProp): JSX.Element {
        if (prop.labels) {
            if (prop.labels.length <= 3) {
                return (
                    <RadioGroup >
                        {prop.labels.map((label, i) => <Radio key={i} value={i}>{label}</Radio>)}
                    </RadioGroup>
                );
            }

            return (
                <Select style={{ minWidth: 200 }}>
                    {prop.labels.map((label, i) => <Option key={i} value={i}>{label}</Option>)}
                </Select>
            );
        }

        if (prop.values) {
            const keys = Object.keys(prop.values);
            if (keys.length <= 3) {
                return (
                    <RadioGroup >
                        {keys.map((key) => <Radio key={key} value={key}>{(prop.values || {})[key]}</Radio>)}
                    </RadioGroup>
                );
            }

            return (
                <Select style={{ minWidth: 200 }}>
                    {keys.map((key) => <Option key={key} value={key}>{(prop.values || {})[key]}</Option>)}
                </Select>
            );
        }

        if (prop.type === 'date') {
            return <DatePicker format={DateFormat} />;
        }

        if (prop.type === 'date-range') {
            return <DatePicker.RangePicker format={DateFormat} />;
        }

        if (prop.type === 'number') {
            return <InputNumber />;
        }

        if (prop.type === 'read-only') {
            return <Input readOnly={true} />;
        }

        if (prop.type === 'text-area') {
            return <TextArea autosize={{ minRows: 4, maxRows: 16 }} />;
        }

        if (prop.type === 'image') {
            return <Image name={prop.name} upload={prop.upload || (this.key + '.' + prop.name)} />;
        }

        return <Input />;
    }

    public getModelValue(model?: Model, name?: string): any {
        if (!model || !name) {
            return '';
        }

        if (model.hasOwnProperty(name)) {
            return model[name];
        }

        if (name.indexOf('.') === -1) {
            return '';
        }

        let obj: any = model;
        for (const n of name.split('.')) {
            if (!obj.hasOwnProperty(n)) {
                return '';
            }

            obj = obj[n];
        }

        return obj;
    }

    public getFormValue(form: any, props: MetaProp[]): {} {
        const obj = {};
        const { getFieldValue } = form;
        props.map(prop => {
            if (prop.type === 'read-only') {
                return;
            }

            if (prop.type === 'image') {
                obj[prop.name] = getImageValue(prop.name);

                return;
            }

            const value: any = getFieldValue(prop.name);
            if (value === null || value === undefined) {
                return;
            }

            obj[prop.name] = value;
            if (prop.type === 'date') {
                obj[prop.name] = value.format(DateFormat);
            } else if (prop.type === 'date-range') {
                obj[prop.name] = value.length === 0 ? '' : (value[0].format(DateFormat) + ',' + value[1].format(DateFormat));
            } else if (prop.type === 'money') {
                obj[prop.name] = Math.round(value * 100);
            }
        });

        return obj;
    }

    public getService(key: string, action: Action, service?: string): string {
        const k = service || action.service || '.' + action.type;

        return k.charAt(0) === '.' ? (key.substring(0, key.lastIndexOf('.')) + k) : k;
    }

    public success(key: string, action: Action, header?: {}, parameter?: {}, model?: {}): void {
        if (!action.success) {
            return;
        }

        if (typeof action.success === 'string') {
            pager.to(this.getService(key, action, action.success), header, parameter);

            return;
        }

        const param = parameter || {};
        if (action.success.parameter) {
            if (!model) {
                model = {};
            }
            for (const name in action.success.parameter) {
                if (action.success.parameter.hasOwnProperty(name)) {
                    param[name] = model[name] || action.success.parameter[name];
                }
            }
        }

        pager.to(this.getService(key, action, action.success.service), merger.merge(header || {}, action.success.header || {}), param);
    }

    public post(key: string, header: object = {}, parameter: object = {}): Promise<any> {
        return service.post('/ui/console/service', merger.merge({}, header, { key: key }), parameter);
    }
}

export const pager = new Pager();