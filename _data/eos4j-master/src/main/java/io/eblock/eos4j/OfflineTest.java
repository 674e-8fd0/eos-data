package io.eblock.eos4j;

import io.eblock.eos4j.api.exception.ApiException;
import io.eblock.eos4j.api.vo.SignParam;
import io.eblock.eos4j.api.vo.transaction.Transaction;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public class OfflineTest {

	public static void main(String[] args) {
		 testOfflineCreate();
		//testOfflineTransfer();
	}

	public static void testOfflineCreate() {
		Rpc rpc = new Rpc("http://127.0.0.1:8888");
		// 获取离线签名参数
		SignParam params = rpc.getOfflineSignParams(60l);
		// 离线签名
		OfflineSign sign = new OfflineSign();
		// 交易信息SIG_K1_KiUUL4pSc82JKThxNfjsxXVPRxVSsjv4UPEHNjkxEwVXGaGfN7rJj3eHfd2wZ5t7f4Xot2FMTbEkFFfpKE5FrgrbRwhHZs

		String content = "";
		try {
			content = sign.createAccount(params, "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3", "eeeeeeeeeeee",
					"55", "EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV",
					"EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV", 8000l);
			System.out.println("text"+content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 广播交易
		try {
			Transaction tx = rpc.pushTransaction(content);
			System.out.println(tx.getTransactionId());
		} catch (ApiException ex) {
			System.out.println(ex.getError().getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testOfflineTransfer() {
		Rpc rpc = new Rpc("http://127.0.0.1:8888");
		// 获取离线签名参数
		SignParam params = rpc.getOfflineSignParams(60l);
		// 离线签名
		OfflineSign sign = new OfflineSign();
		// 交易信息
		String content = "";
		try {
			content = sign.transfer(params, "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3", "eosio.token",
					"eeeeeeeeeeee", "555555555551", "372.0993 EOS", "test");
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 广播交易
		try {
			Transaction tx = rpc.pushTransaction(content);
			System.out.println(tx.getTransactionId());
		} catch (ApiException ex) {
			System.out.println(ex.getError().getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
