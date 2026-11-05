import sys
import os
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

import src.digitalWalletSystem as module_0


def setup_function():
    module_0.users_db.clear()


def test_new_0_register_valid():
    status = module_0.register_user("A", "a@test.com", "1234", 10.0)
    assert status.success is True
    assert "registered successfully" in status.message
    assert "a@test.com" in module_0.users_db


def test_new_1_register_invalid_email():
    status = module_0.register_user("A", "invalid", "1234", 10.0)
    assert status.success is False
    assert status.message == "Invalid registration input."
    assert module_0.users_db == {}


def test_new_2_register_invalid_pin():
    status = module_0.register_user("A", "a@test.com", "12ab", 10.0)
    assert status.success is False
    assert status.message == "Invalid registration input."


def test_new_3_login_success():
    module_0.register_user("B", "b@test.com", "1111", 20.0)
    login = module_0.authenticate_user("b@test.com", "1111")
    assert login.success is True
    assert login.message == "Login successful."
    assert module_0.users_db["b@test.com"].logged_in is True


def test_new_4_login_wrong_pin():
    module_0.register_user("C", "c@test.com", "2222", 30.0)
    login = module_0.authenticate_user("c@test.com", "0000")
    assert login.success is False
    assert "Incorrect PIN" in login.message


def test_new_5_view_balance_logged_in():
    module_0.register_user("D", "d@test.com", "3333", 40.0)
    module_0.authenticate_user("d@test.com", "3333")
    info = module_0.view_balance("d@test.com")
    assert info.balance == 40.0
    assert info.transactions == []
    assert info.message == "Balance retrieved successfully."


def test_new_6_deposit_success():
    module_0.register_user("E", "e@test.com", "4444", 5.0)
    module_0.authenticate_user("e@test.com", "4444")
    status = module_0.deposit("e@test.com", 15.0)
    assert status.success is True
    assert module_0.users_db["e@test.com"].balance == 20.0


def test_new_7_deposit_invalid_amount():
    module_0.register_user("F", "f@test.com", "5555", 5.0)
    module_0.authenticate_user("f@test.com", "5555")
    status = module_0.deposit("f@test.com", -10.0)
    assert status.success is False
    assert status.message == "Deposit amount must be positive."


def test_new_8_withdraw_success():
    module_0.register_user("G", "g@test.com", "6666", 100.0)
    module_0.authenticate_user("g@test.com", "6666")
    status = module_0.withdraw("g@test.com", 40.0)
    assert status.success is True
    assert module_0.users_db["g@test.com"].balance == 60.0


def test_new_9_withdraw_not_logged_in():
    module_0.register_user("H", "h@test.com", "7777", 50.0)
    status = module_0.withdraw("h@test.com", 10.0)
    assert status.success is False
    assert status.message == "User not logged in."


def test_new_10_interest_success():
    module_0.register_user("I", "i@test.com", "8888", 100.0)
    module_0.authenticate_user("i@test.com", "8888")
    status = module_0.apply_interest("i@test.com", 0.1)
    assert status.success is True
    assert module_0.users_db["i@test.com"].balance == 110.0


def test_new_11_interest_zero_balance():
    module_0.register_user("J", "j@test.com", "9999", 0.0)
    module_0.authenticate_user("j@test.com", "9999")
    status = module_0.apply_interest("j@test.com", 0.1)
    assert status.success is False
    assert status.message == "No interest accrued on zero or negative balance."


def test_new_12_transfer_success():
    module_0.register_user("K", "k@test.com", "1212", 70.0)
    module_0.register_user("L", "l@test.com", "3434", 10.0)
    module_0.authenticate_user("k@test.com", "1212")
    module_0.authenticate_user("l@test.com", "3434")
    status = module_0.transfer("k@test.com", "l@test.com", 20.0)
    assert status.success is True
    assert module_0.users_db["k@test.com"].balance == 50.0
    assert module_0.users_db["l@test.com"].balance == 30.0


def test_new_13_transfer_not_logged_in():
    module_0.register_user("M", "m@test.com", "5656", 30.0)
    module_0.register_user("N", "n@test.com", "7878", 30.0)
    status = module_0.transfer("m@test.com", "n@test.com", 10.0)
    assert status.success is False
    assert status.message == "Both users must be logged in."
