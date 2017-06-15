package org.lpw.ranch.account;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.lpw.ranch.account.log.LogModel;
import org.lpw.tephra.util.TimeUnit;

/**
 * @author lpw
 */
public class ConsumeTest extends TestSupport {
    @Test
    public void consume() {
        validate("consume", 9);

        AccountModel account = new AccountModel();
        account.setUser("user 1");
        account.setOwner("owner 1");
        liteOrm.save(account);
        mockUser();
        mockHelper.reset();
        mockHelper.getRequest().addParameter("user", "user 1");
        mockHelper.getRequest().addParameter("owner", "owner 1");
        mockHelper.getRequest().addParameter("amount", "1");
        mockHelper.getRequest().addParameter("label", "label 1");
        mockHelper.mock("/account/consume");
        JSONObject object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2209, object.getIntValue("code"));
        Assert.assertEquals(message.get(AccountModel.NAME + ".consume.failure"), object.getString("message"));

        account.setBalance(2);
        liteOrm.save(account);
        mockUser();
        mockHelper.reset();
        mockHelper.getRequest().addParameter("user", "user 1");
        mockHelper.getRequest().addParameter("owner", "owner 1");
        mockHelper.getRequest().addParameter("amount", "1");
        mockHelper.getRequest().addParameter("label", "label 1");
        mockHelper.mock("/account/consume");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        JSONObject data = object.getJSONObject("data");
        Assert.assertEquals(12, data.size());
        Assert.assertEquals("user 1", data.getString("user"));
        Assert.assertEquals("owner 1", data.getString("owner"));
        for (String property : new String[]{"type", "deposit", "withdraw", "reward", "profit", "consume"})
            Assert.assertEquals(0, data.getIntValue(property));
        for (String property : new String[]{"balance", "pending"})
            Assert.assertEquals(1, data.getIntValue(property));
        account = liteOrm.findById(AccountModel.class, data.getString("id"));
        Assert.assertEquals("user 1", account.getUser());
        Assert.assertEquals("owner 1", account.getOwner());
        Assert.assertEquals(0, account.getType());
        Assert.assertEquals(1, account.getBalance());
        Assert.assertEquals(0, account.getDeposit());
        Assert.assertEquals(0, account.getWithdraw());
        Assert.assertEquals(0, account.getReward());
        Assert.assertEquals(0, account.getProfit());
        Assert.assertEquals(0, account.getConsume());
        Assert.assertEquals(1, account.getPending());
        Assert.assertEquals(digest.md5(AccountModel.NAME + ".service.checksum&user 1&owner 1&0&1&0&0&0&0&0&1"), account.getChecksum());
        LogModel log = liteOrm.findById(LogModel.class, data.getString("logId"));
        Assert.assertEquals("user 1", log.getUser());
        Assert.assertEquals(account.getId(), log.getAccount());
        Assert.assertEquals("consume", log.getType());
        Assert.assertEquals(1, log.getAmount());
        Assert.assertEquals(1, log.getBalance());
        Assert.assertEquals(0, log.getState());
        Assert.assertTrue(System.currentTimeMillis() - log.getStart().getTime() < 2000L);
        Assert.assertNull(log.getEnd());
        Assert.assertNull(log.getJson());

        thread.sleep(3, TimeUnit.Second);
        log.setAmount(2);
        liteOrm.save(log);
        mockHelper.reset();
        mockHelper.getRequest().addParameter("ids", log.getId());
        sign.put(mockHelper.getRequest().getMap(),null);
        mockHelper.mock("/account/log/pass");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        Assert.assertEquals("", object.getString("data"));
        account = liteOrm.findById(AccountModel.class, account.getId());
        Assert.assertEquals("user 1", account.getUser());
        Assert.assertEquals("owner 1", account.getOwner());
        Assert.assertEquals(0, account.getType());
        Assert.assertEquals(1, account.getBalance());
        Assert.assertEquals(0, account.getDeposit());
        Assert.assertEquals(0, account.getWithdraw());
        Assert.assertEquals(0, account.getReward());
        Assert.assertEquals(0, account.getProfit());
        Assert.assertEquals(0, account.getConsume());
        Assert.assertEquals(1, account.getPending());
        Assert.assertEquals(digest.md5(AccountModel.NAME + ".service.checksum&user 1&owner 1&0&1&0&0&0&0&0&1"), account.getChecksum());
        log = liteOrm.findById(LogModel.class, log.getId());
        Assert.assertEquals("user 1", log.getUser());
        Assert.assertEquals(account.getId(), log.getAccount());
        Assert.assertEquals("consume", log.getType());
        Assert.assertEquals(2, log.getAmount());
        Assert.assertEquals(1, log.getBalance());
        Assert.assertEquals(0, log.getState());
        Assert.assertTrue(System.currentTimeMillis() - log.getStart().getTime() > 2000L);
        Assert.assertNull(log.getEnd());
        Assert.assertNull(log.getJson());

        log.setAmount(1);
        liteOrm.save(log);
        mockHelper.reset();
        mockHelper.getRequest().addParameter("ids", log.getId());
        sign.put(mockHelper.getRequest().getMap(),null);
        mockHelper.mock("/account/log/pass");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        Assert.assertEquals("", object.getString("data"));
        account = liteOrm.findById(AccountModel.class, account.getId());
        Assert.assertEquals("user 1", account.getUser());
        Assert.assertEquals("owner 1", account.getOwner());
        Assert.assertEquals(0, account.getType());
        Assert.assertEquals(1, account.getBalance());
        Assert.assertEquals(0, account.getDeposit());
        Assert.assertEquals(0, account.getWithdraw());
        Assert.assertEquals(0, account.getReward());
        Assert.assertEquals(0, account.getProfit());
        Assert.assertEquals(1, account.getConsume());
        Assert.assertEquals(0, account.getPending());
        Assert.assertEquals(digest.md5(AccountModel.NAME + ".service.checksum&user 1&owner 1&0&1&0&0&0&0&1&0"), account.getChecksum());
        log = liteOrm.findById(LogModel.class, log.getId());
        Assert.assertEquals("user 1", log.getUser());
        Assert.assertEquals(account.getId(), log.getAccount());
        Assert.assertEquals("consume", log.getType());
        Assert.assertEquals(1, log.getAmount());
        Assert.assertEquals(1, log.getBalance());
        Assert.assertEquals(1, log.getState());
        Assert.assertTrue(System.currentTimeMillis() - log.getStart().getTime() > 2000L);
        Assert.assertTrue(System.currentTimeMillis() - log.getEnd().getTime() < 2000L);
        Assert.assertNull(log.getJson());

        account = new AccountModel();
        account.setUser("sign in id");
        account.setOwner("owner 2");
        account.setBalance(2);
        liteOrm.save(account);

        mockUser();
        mockCarousel.register("ranch.user.sign", "{\"code\":0,\"data\":{\"id\":\"sign in id\"}}");
        mockHelper.reset();
        mockHelper.getRequest().addParameter("owner", "owner 2");
        mockHelper.getRequest().addParameter("amount", "2");
        mockHelper.getRequest().addParameter("label", "label 2");
        mockHelper.mock("/account/consume");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        data = object.getJSONObject("data");
        Assert.assertEquals(12, data.size());
        Assert.assertEquals("sign in id", data.getString("user"));
        Assert.assertEquals("owner 2", data.getString("owner"));
        for (String property : new String[]{"type", "balance", "deposit", "withdraw", "reward", "profit", "consume"})
            Assert.assertEquals(0, data.getIntValue(property));
        Assert.assertEquals(2, data.getIntValue("pending"));
        account = liteOrm.findById(AccountModel.class, data.getString("id"));
        Assert.assertEquals("sign in id", account.getUser());
        Assert.assertEquals("owner 2", account.getOwner());
        Assert.assertEquals(0, account.getType());
        Assert.assertEquals(0, account.getBalance());
        Assert.assertEquals(0, account.getDeposit());
        Assert.assertEquals(0, account.getWithdraw());
        Assert.assertEquals(0, account.getReward());
        Assert.assertEquals(0, account.getProfit());
        Assert.assertEquals(0, account.getConsume());
        Assert.assertEquals(2, account.getPending());
        Assert.assertEquals(digest.md5(AccountModel.NAME + ".service.checksum&sign in id&owner 2&0&0&0&0&0&0&0&2"), account.getChecksum());
        log = liteOrm.findById(LogModel.class, data.getString("logId"));
        Assert.assertEquals("sign in id", log.getUser());
        Assert.assertEquals(account.getId(), log.getAccount());
        Assert.assertEquals("consume", log.getType());
        Assert.assertEquals(2, log.getAmount());
        Assert.assertEquals(0, log.getBalance());
        Assert.assertEquals(0, log.getState());
        Assert.assertTrue(System.currentTimeMillis() - log.getStart().getTime() < 2000L);
        Assert.assertNull(log.getEnd());
        Assert.assertNull(log.getJson());

        thread.sleep(3, TimeUnit.Second);
        String lockId = lockHelper.lock(AccountModel.NAME + ".service.lock:sign in id-owner 2-0", 1000L);
        mockHelper.reset();
        mockHelper.getRequest().addParameter("ids", log.getId());
        sign.put(mockHelper.getRequest().getMap(),null);
        mockHelper.mock("/account/log/reject");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        Assert.assertEquals("", object.getString("data"));
        account = liteOrm.findById(AccountModel.class, account.getId());
        Assert.assertEquals("sign in id", account.getUser());
        Assert.assertEquals("owner 2", account.getOwner());
        Assert.assertEquals(0, account.getType());
        Assert.assertEquals(0, account.getBalance());
        Assert.assertEquals(0, account.getDeposit());
        Assert.assertEquals(0, account.getWithdraw());
        Assert.assertEquals(0, account.getReward());
        Assert.assertEquals(0, account.getProfit());
        Assert.assertEquals(0, account.getConsume());
        Assert.assertEquals(2, account.getPending());
        Assert.assertEquals(digest.md5(AccountModel.NAME + ".service.checksum&sign in id&owner 2&0&0&0&0&0&0&0&2"), account.getChecksum());
        log = liteOrm.findById(LogModel.class, data.getString("logId"));
        Assert.assertEquals("sign in id", log.getUser());
        Assert.assertEquals(account.getId(), log.getAccount());
        Assert.assertEquals("consume", log.getType());
        Assert.assertEquals(2, log.getAmount());
        Assert.assertEquals(0, log.getBalance());
        Assert.assertEquals(0, log.getState());
        Assert.assertTrue(System.currentTimeMillis() - log.getStart().getTime() > 2000L);
        Assert.assertNull(log.getEnd());
        Assert.assertNull(log.getJson());
        lockHelper.unlock(lockId);

        mockHelper.reset();
        mockHelper.getRequest().addParameter("ids", log.getId());
        sign.put(mockHelper.getRequest().getMap(),null);
        mockHelper.mock("/account/log/reject");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        Assert.assertEquals("", object.getString("data"));
        account = liteOrm.findById(AccountModel.class, account.getId());
        Assert.assertEquals("sign in id", account.getUser());
        Assert.assertEquals("owner 2", account.getOwner());
        Assert.assertEquals(0, account.getType());
        Assert.assertEquals(2, account.getBalance());
        Assert.assertEquals(0, account.getDeposit());
        Assert.assertEquals(0, account.getWithdraw());
        Assert.assertEquals(0, account.getReward());
        Assert.assertEquals(0, account.getProfit());
        Assert.assertEquals(0, account.getConsume());
        Assert.assertEquals(0, account.getPending());
        Assert.assertEquals(digest.md5(AccountModel.NAME + ".service.checksum&sign in id&owner 2&0&2&0&0&0&0&0&0"), account.getChecksum());
        log = liteOrm.findById(LogModel.class, data.getString("logId"));
        Assert.assertEquals("sign in id", log.getUser());
        Assert.assertEquals(account.getId(), log.getAccount());
        Assert.assertEquals("consume", log.getType());
        Assert.assertEquals(2, log.getAmount());
        Assert.assertEquals(2, log.getBalance());
        Assert.assertEquals(2, log.getState());
        Assert.assertTrue(System.currentTimeMillis() - log.getStart().getTime() > 2000L);
        Assert.assertTrue(System.currentTimeMillis() - log.getEnd().getTime() < 2000L);
        Assert.assertNull(log.getJson());
    }
}
