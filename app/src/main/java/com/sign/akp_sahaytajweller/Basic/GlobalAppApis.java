package com.sign.akp_sahaytajweller.Basic;

import org.json.JSONException;
import org.json.JSONObject;

public class GlobalAppApis {
    public String Login(String Password,String UserName) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("TokenNo", "");
            jsonObject1.put("Password", Password);
            jsonObject1.put("UserName", UserName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String NewAccount(String Country, String Email,String FullName,String MobilNo,String Password,String SponsorId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Country", Country);
            jsonObject1.put("EmailAddress", Email);
            jsonObject1.put("Name", FullName);
            jsonObject1.put("ContactNo", MobilNo);
            jsonObject1.put("Password", Password);
            jsonObject1.put("SponsorId", SponsorId);
            jsonObject1.put("Gender", "Male");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String CheckSponsor(String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String EMIChartDetails(String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String GetProfarmaDetails(String UserId,String ProfarmaNumber) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("ProfarmaNumber", ProfarmaNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String UpdateProfile(String action, String Country, String Email,String FullName,String MobilNo,String NewPassword,
                                String OldPassword,String SponsorId,String USDTAddress,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Country", Country);
            jsonObject1.put("Email", Email);
            jsonObject1.put("FullName", FullName);
            jsonObject1.put("MobilNo", MobilNo);
            jsonObject1.put("NewPassword", NewPassword);
            jsonObject1.put("OldPassword", OldPassword);
            jsonObject1.put("SponsorId", SponsorId);
            jsonObject1.put("USDTAddress", USDTAddress);
            jsonObject1.put("UserId", UserId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String ChangePassword(String NewPassword,
                                String OldPassword,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("NewPass", NewPassword);
            jsonObject1.put("OldPass", OldPassword);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String ChangeTransactionPassword(String NewPassword,
                                 String OldPassword,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("NewPass", NewPassword);
            jsonObject1.put("OldPass", OldPassword);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String Dashboard(String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("PackageId", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }





    public String Profile(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String Wallet(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String TopupDetails(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String MyReferral(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String MyTeam(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }



    public String DirectIncomeAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String LevelIncomeAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }



    public String ContractIncomeAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String ShowWithdrwarAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String WalletHistoryAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String AccountActivationAPI(String ActivationId,String Amount,String Password,String TransactionHash,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("ActivationId", ActivationId);
            jsonObject1.put("PacakgeId", Amount);
            jsonObject1.put("Password", Password);
            jsonObject1.put("TransactionHash", TransactionHash);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String FundRequestAPI(String Amount,String ReceiptImg,String TransactionHash,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Amount", Amount);
            jsonObject1.put("ReceiptImg", ReceiptImg);
            jsonObject1.put("TransactionHash", TransactionHash);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String GetFundHistory(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String GetPerformanceIncome(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String GetClubLeaderIncomeReport(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String RewardIncome(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String Inbox(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String Outbox(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String MyRefferal(String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("UserId", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String TransferHistory(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }





    public String MainWallet(String CashWallet,String UserId,String WalletBalance) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("CashWallet", CashWallet);
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("WalletBalance", WalletBalance);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String P2PTransfer(String Amount,String ReceiverId,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Amount", Amount);
            jsonObject1.put("ReceiverId", ReceiverId);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String Support(String Message,String Subject,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Message", Message);
            jsonObject1.put("Subject", Subject);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String WithdrawlRequest(String Mode,String RequestWalletAmt,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Mode", Mode);
            jsonObject1.put("Amount", RequestWalletAmt);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String GetPrinciplePackageList(String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String PrincipalWithdrawlRequestAPI(String Balance,String PacakgeId,String RequestWalletAmt,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Balance", Balance);
            jsonObject1.put("PacakgeId", PacakgeId);
            jsonObject1.put("RequestWalletAmt", RequestWalletAmt);
            jsonObject1.put("UserId", UserId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String EMIRepayment(String ProfarmaNo,String MemberId,String InstallmentNo,String EMIAmount,String Description,String paymentmode) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("ProfarmaNo", ProfarmaNo);
            jsonObject1.put("MemberId", MemberId);
            jsonObject1.put("InstallmentNo", InstallmentNo);
            jsonObject1.put("EMIAmount", EMIAmount);
            jsonObject1.put("Description", Description);
            jsonObject1.put("paymentmode", paymentmode);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


}

