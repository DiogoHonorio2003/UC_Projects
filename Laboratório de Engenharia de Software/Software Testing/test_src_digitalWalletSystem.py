import sys
import os
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

import src.digitalWalletSystem as module_0

def test_case_0():
    str_0 = "1PI+ x"
    account_status_0 = module_0.register_user(str_0, str_0, str_0, str_0)
    assert (
        f"{type(account_status_0).__module__}.{type(account_status_0).__qualname__}"
        == "src.digitalWalletSystem.AccountStatus"
    )
    assert account_status_0.success is False
    assert account_status_0.message == "Invalid registration input."
    assert module_0.users_db == {}


def test_case_1():
    str_0 = "1PI+ x"
    login_status_0 = module_0.authenticate_user(str_0, str_0)
    assert login_status_0.message == "Invalid email or PIN format."
    assert module_0.users_db == {}


def test_case_2():
    str_0 = "2"
    login_status_0 = module_0.authenticate_user(str_0, str_0)
    assert login_status_0.message == "Invalid email or PIN format."
    assert module_0.users_db == {}


def test_case_3():
    str_0 = "1PI+ x"
    login_status_0 = module_0.authenticate_user(str_0, str_0)
    assert login_status_0.message == "Invalid email or PIN format."
    assert module_0.users_db == {}


def test_case_4():
    str_0 = "1PI+ x"
    deposit_status_0 = module_0.deposit(str_0, str_0)
    assert (
        f"{type(deposit_status_0).__module__}.{type(deposit_status_0).__qualname__}"
        == "src.digitalWalletSystem.DepositStatus"
    )
    assert deposit_status_0.success is False
    assert deposit_status_0.message == "User does not exist."
    assert module_0.users_db == {}


def test_case_5():
    float_0 = -3110.009017391711
    interest_status_0 = module_0.apply_interest(float_0, float_0)
    assert (
        f"{type(interest_status_0).__module__}.{type(interest_status_0).__qualname__}"
        == "src.digitalWalletSystem.InterestStatus"
    )
    assert interest_status_0.success is False
    assert interest_status_0.message == "User does not exist."
    assert module_0.users_db == {}


def test_case_6():
    str_0 = "1PI+ x"
    transfer_status_0 = module_0.transfer(str_0, str_0, str_0)
    assert transfer_status_0.message == "Sender or receiver does not exist."
    assert module_0.users_db == {}


def test_case_7():
    str_0 = "F!\x0b}ePU"
    withdrawal_status_0 = module_0.withdraw(str_0, str_0)
    assert (
        f"{type(withdrawal_status_0).__module__}.{type(withdrawal_status_0).__qualname__}"
        == "src.digitalWalletSystem.WithdrawalStatus"
    )
    assert withdrawal_status_0.success is False
    assert withdrawal_status_0.message == "User does not exist."
    assert module_0.users_db == {}


def test_case_8():
    none_type_0 = None
    str_0 = "x\\"
    account_status_0 = module_0.register_user(
        none_type_0, str_0, none_type_0, none_type_0
    )
    assert (
        f"{type(account_status_0).__module__}.{type(account_status_0).__qualname__}"
        == "src.digitalWalletSystem.AccountStatus"
    )
    assert account_status_0.success is False
    assert account_status_0.message == "Invalid registration input."
    assert module_0.users_db == {}
    str_1 = "4"
    balance_info_0 = module_0.view_balance(str_1)
    assert (
        f"{type(balance_info_0).__module__}.{type(balance_info_0).__qualname__}"
        == "src.digitalWalletSystem.BalanceInfo"
    )
    assert balance_info_0.balance == 0
    assert balance_info_0.transactions == []
    assert balance_info_0.interest == 0
    assert balance_info_0.message == "User does not exist."


def test_case_9():
    bool_0 = True
    balance_info_0 = module_0.view_balance(bool_0)
    assert (
        f"{type(balance_info_0).__module__}.{type(balance_info_0).__qualname__}"
        == "src.digitalWalletSystem.BalanceInfo"
    )
    assert balance_info_0.balance == 0
    assert balance_info_0.transactions == []
    assert balance_info_0.interest == 0
    assert balance_info_0.message == "User does not exist."
    assert module_0.users_db == {}


def test_case_10():
    str_0 = "U"
    account_status_0 = module_0.register_user(str_0, str_0, str_0, str_0)
    assert (
        f"{type(account_status_0).__module__}.{type(account_status_0).__qualname__}"
        == "src.digitalWalletSystem.AccountStatus"
    )
    assert account_status_0.success is False
    assert account_status_0.message == "Invalid registration input."
    assert module_0.users_db == {}


def test_case_11():
    str_0 = "1PI+ x"
    balance_info_0 = module_0.view_balance(str_0)
    assert (
        f"{type(balance_info_0).__module__}.{type(balance_info_0).__qualname__}"
        == "src.digitalWalletSystem.BalanceInfo"
    )
    assert balance_info_0.balance == 0
    assert balance_info_0.transactions == []
    assert balance_info_0.interest == 0
    assert balance_info_0.message == "User does not exist."
    assert module_0.users_db == {}
    module_0.valid_pin(str_0)
