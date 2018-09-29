import * as React from 'react';
import { Form, Button } from 'antd';
import merger from '../../util/merger';
import { ActionMeta } from '../meta';
import { pager } from '../pager';
import { PageState } from '../page';
import './index.scss';

interface Props extends PageState {
    form: any;
}

class FormPage extends React.Component<Props> {
    public render(): JSX.Element {
        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 8 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 16 },
            },
        };

        return <Form>
            {this.props.props.map(prop =>
                <Form.Item key={prop.name} label={prop.label} {...formItemLayout}>
                    {pager.getInput(this.props.form, prop, this.props.data)}
                </Form.Item>
            )}
            <Form.Item
                wrapperCol={{
                    xs: { span: 24, offset: 0 },
                    sm: { span: 16, offset: 8 },
                }}
            >
                {this.props.meta.toolbar ? this.props.meta.toolbar.map(button =>
                    <Button key={button.service || button.type} type="primary" icon={button.icon} onClick={this.click.bind(this, button)}>{button.label}</Button>
                ) : null}
            </Form.Item>
        </Form>;
    }

    private click(action: ActionMeta): void {
        if (action.type === 'save' || action.type === 'modify') {
            pager.post({
                service: pager.getService(this.props.service, action),
                header: merger.merge({}, this.props.header || {}),
                parameter: merger.merge({}, this.props.data, pager.getFormValue(this.props.form, this.props.props), this.props.parameter || {})
            }).then(data => {
                if (data === null) {
                    return;
                }

                pager.success(this.props.service, action, this.props.header, this.props.parameter);
            });

            return;
        }

        console.log(action);
    }
}

export default Form.create()(FormPage);