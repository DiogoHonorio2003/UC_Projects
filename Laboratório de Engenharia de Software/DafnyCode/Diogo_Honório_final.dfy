datatype AccountStatus = Success | Failure
datatype LoginStatus = LoginSuccess | LoginFailure | AccountLocked
datatype BalanceStatus = BalanceSuccess | BalanceFailure
datatype BalanceInfo = BalanceInfo(balance: real, status: BalanceStatus, message: string)
datatype DepositStatus = DepositSuccess | DepositFailure
datatype InterestStatus = InterestSuccess | InterestNoAccrual
datatype InterestResult = InterestResult(status: InterestStatus, message: string, new_balance: real)
datatype TransferStatus = TransferSuccess | TransferFailure
datatype WithdrawalStatus = WithdrawalSuccess | WithdrawalFailure

class DigitalWallet {
  var users_db: map<string, (string, string, real, bool, int)> 
  var logged_in_users: set<string> // Tracks currently logged in users
  
  predicate Valid()
    reads this
  {
    (forall e :: e in users_db ==> 
      |users_db[e].1| == 4 && // pin length = 4
      users_db[e].2 >= 0.0 && // balance non-negative
      users_db[e].4 >= 0) &&   // attempts non-negative
      logged_in_users <= users_db.Keys // All logged in users exist in db
  }

  constructor()
    ensures Valid() && users_db == map[] && logged_in_users == {}
  {
    users_db := map[];
    logged_in_users := {};
  }
  
  // Create new account Method
  method RegisterUser(name: string, email: string, pin: string, initial_balance: real) returns (status: AccountStatus)
    requires Valid()
    requires name != ""
    requires email != "" && exists i :: 0 <= i < |email| && email[i] == '@' && exists j :: 0 <= j < |email| && email[j] == '.'
    requires |pin| == 4 && (forall i :: 0 <= i < 4 ==> '0' <= pin[i] <= '9')
    requires initial_balance >= 0.0
    requires email !in users_db
    modifies this
    ensures Valid()
    ensures status == Success ==> 
              email in users_db &&
              users_db[email].0 == name &&
              users_db[email].1 == pin &&
              users_db[email].2 == initial_balance
    ensures status == Failure ==> users_db == old(users_db)
  {
    if email in users_db {
      status := Failure;
    } else {
      users_db := users_db[email := (name, pin, initial_balance, false, 0)];
      status := Success;
    }
  }

  // Authenticate user Method
  method AuthenticateUser(email: string, entered_pin: string) returns (status: LoginStatus)
    requires Valid()
    requires email != "" && exists i :: 0 <= i < |email| && email[i] == '@' && exists j :: 0 <= j < |email| && email[j] == '.'
    requires email in users_db
    requires |entered_pin| == 4 && (forall i :: 0 <= i < 4 ==> '0' <= entered_pin[i] <= '9')
    requires users_db[email].4 >= 0
    modifies this
    ensures Valid()
    ensures
      // Success case
      status == LoginSuccess ==>
        email in users_db &&
        users_db[email].4 == 0 &&
        users_db[email].3 == false
    ensures
      // Failure case
      status == LoginFailure ==>
        email in users_db &&
        email in old(users_db) &&
        users_db[email].4 == old(users_db[email].4) + 1 &&
        users_db[email].3 == false
    ensures
      // Locked case
      status == AccountLocked ==>
        email in users_db &&
        email in old(users_db) &&
        users_db[email].3 == true &&
        (users_db[email].4 >= 5 || old(users_db[email].3))
  {
    var u := users_db[email];
    var name := u.0;
    var pin := u.1;
    var balance := u.2;
    var locked := u.3;
    var attempts := u.4;

    if locked {
      status := AccountLocked;
    } else if entered_pin == pin {
      // Correct PIN
      users_db := users_db[email := (name, pin, balance, false, 0)];
      status := LoginSuccess;
    } else {
      // Wrong PIN
      var newAttempts := attempts + 1;
      if newAttempts >= 5 {
        users_db := users_db[email := (name, pin, balance, true, newAttempts)];
        status := AccountLocked;
      } else {
        users_db := users_db[email := (name, pin, balance, false, newAttempts)];
        status := LoginFailure;
      }
    }
  }

  // Consult Balance Method
  method ConsultBalance(email: string) returns (balanceInfo: BalanceInfo)
    requires Valid()
    requires email != "" && exists i :: 0 <= i < |email| && email[i] == '@' && exists j :: 0 <= j < |email| && email[j] == '.'
    requires email in users_db
    ensures Valid()
    ensures users_db == old(users_db) && logged_in_users == old(logged_in_users) 
    ensures
      // Success case - user is logged in
      email in old(logged_in_users) ==>
        balanceInfo.status == BalanceSuccess &&
        balanceInfo.balance == old(users_db[email].2) &&
        balanceInfo.message == "Balance successfully retrieved"
    ensures
      // Failure case - user is not logged in
      email !in old(logged_in_users) ==>
        balanceInfo.status == BalanceFailure &&
        balanceInfo.balance == 0.0 &&
        balanceInfo.message == "Access denied: User not logged in"
  {
    if email in logged_in_users {
      var user_data := users_db[email];
      var balance := user_data.2;
      balanceInfo := BalanceInfo(balance, BalanceSuccess, "Balance successfully retrieved");
    } else {
      balanceInfo := BalanceInfo(0.0, BalanceFailure, "Access denied: User not logged in");
    }
  }

  // Deposit Funds Method
  method Deposit(email: string, amount: real) returns (status: DepositStatus)
    requires Valid()
    requires email != "" && exists i :: 0 <= i < |email| && email[i] == '@' && exists j :: 0 <= j < |email| && email[j] == '.'
    requires email in users_db
    requires email in logged_in_users 
    requires amount >= 0.0 
    modifies this
    ensures Valid()
    ensures
      // Success case - amount > 0
      amount > 0.0 ==>
        status == DepositSuccess &&
        email in users_db &&
        users_db[email].2 == old(users_db[email].2) + amount &&
        users_db[email].0 == old(users_db[email].0) && 
        users_db[email].1 == old(users_db[email].1) && 
        users_db[email].3 == old(users_db[email].3) && 
        users_db[email].4 == old(users_db[email].4) && 
        logged_in_users == old(logged_in_users)
    ensures
      // Failure case - amount <= 0
      amount <= 0.0 ==>
        status == DepositFailure &&
        users_db == old(users_db) &&
        logged_in_users == old(logged_in_users)
  {
    if amount <= 0.0 {
      status := DepositFailure;
    } else {
      var user_data := users_db[email];
      var name := user_data.0;
      var pin := user_data.1;
      var old_balance := user_data.2;
      var locked := user_data.3;
      var attempts := user_data.4;
      
      var new_balance := old_balance + amount;
      users_db := users_db[email := (name, pin, new_balance, locked, attempts)];
      
      status := DepositSuccess;
    }
  }

  // Interest Accrual Method
  method ApplyInterest(email: string, interest_rate: real) returns (result: InterestResult)
    requires Valid()
    requires email != "" && exists i :: 0 <= i < |email| && email[i] == '@' && exists j :: 0 <= j < |email| && email[j] == '.'
    requires email in users_db
    requires email in logged_in_users
    requires 0.0 <= interest_rate <= 1.0 
    modifies this
    ensures Valid()
    ensures
      // Success case - balance > 0
      old(users_db[email].2) > 0.0 ==>
        result.status == InterestSuccess &&
        email in users_db &&
        users_db[email].2 == old(users_db[email].2) + (old(users_db[email].2) * interest_rate) &&
        users_db[email].0 == old(users_db[email].0) && 
        users_db[email].1 == old(users_db[email].1) && 
        users_db[email].3 == old(users_db[email].3) && 
        users_db[email].4 == old(users_db[email].4) && 
        logged_in_users == old(logged_in_users) &&
        result.new_balance == users_db[email].2
    ensures
      // No accrual case - balance <= 0
      old(users_db[email].2) <= 0.0 ==>
        result.status == InterestNoAccrual &&
        users_db == old(users_db) &&
        logged_in_users == old(logged_in_users) &&
        result.message == "No interest accrued." &&
        result.new_balance == old(users_db[email].2)
  {
    var user_data := users_db[email];
    var old_balance := user_data.2;
    
    if old_balance <= 0.0 {
      result := InterestResult(InterestNoAccrual, "No interest accrued.", old_balance);
    } else {
      var name := user_data.0;
      var pin := user_data.1;
      var locked := user_data.3;
      var attempts := user_data.4;
      
      var interest_amount := old_balance * interest_rate;
      var new_balance := old_balance + interest_amount;
      
      users_db := users_db[email := (name, pin, new_balance, locked, attempts)];
      
      var message := "Interest applied successfully";
      result := InterestResult(InterestSuccess, message, new_balance);
    }
  }

   // Withdraw Funds Method
  method Withdraw(email: string, amount: real) returns (status: WithdrawalStatus)
    requires Valid()
    requires email != "" && exists i :: 0 <= i < |email| && email[i] == '@' && exists j :: 0 <= j < |email| && email[j] == '.'
    requires email in users_db
    requires email in logged_in_users 
    requires amount > 0.0 
    requires users_db[email].2 >= amount 
    modifies this
    ensures Valid()
    ensures
      // Success case
      status == WithdrawalSuccess ==>
        email in users_db &&
        users_db[email].2 == old(users_db[email].2) - amount &&
        users_db[email].0 == old(users_db[email].0) && 
        users_db[email].1 == old(users_db[email].1) && 
        users_db[email].3 == old(users_db[email].3) && 
        users_db[email].4 == old(users_db[email].4) && 
        logged_in_users == old(logged_in_users)
    ensures
      // Failure case - no changes made
      status == WithdrawalFailure ==>
        users_db == old(users_db) &&
        logged_in_users == old(logged_in_users)
  {
    var user_data := users_db[email];
    var name := user_data.0;
    var pin := user_data.1;
    var old_balance := user_data.2;
    var locked := user_data.3;
    var attempts := user_data.4;
    
    var new_balance := old_balance - amount;
    
    users_db := users_db[email := (name, pin, new_balance, locked, attempts)];
    
    
    status := WithdrawalSuccess;
  }

// Transfer Funds Method
method Transfer(sender_email: string, receiver_email: string, amount: real) returns (status: TransferStatus)
  requires Valid()
  requires sender_email != "" && exists i :: 0 <= i < |sender_email| && sender_email[i] == '@' && exists j :: 0 <= j < |sender_email| && sender_email[j] == '.'
  requires receiver_email != "" && exists i :: 0 <= i < |receiver_email| && receiver_email[i] == '@' && exists j :: 0 <= j < |receiver_email| && receiver_email[j] == '.'
  requires sender_email in users_db
  requires receiver_email in users_db
  requires sender_email != receiver_email
  requires sender_email in logged_in_users 
  requires amount > 0.0 
  requires users_db[sender_email].2 >= amount 
  requires users_db[sender_email].2 >= 0.0 && users_db[receiver_email].2 >= 0.0 
  requires !users_db[sender_email].3 && !users_db[receiver_email].3
  modifies this
  ensures Valid()
  ensures
    // Success case - transfer completed
    status == TransferSuccess ==>
      sender_email in users_db &&
      receiver_email in users_db &&
      users_db[sender_email].2 == old(users_db[sender_email].2) - amount &&
      users_db[receiver_email].2 == old(users_db[receiver_email].2) + amount &&
     
      users_db[sender_email].0 == old(users_db[sender_email].0) &&
      users_db[receiver_email].0 == old(users_db[receiver_email].0) &&
     
      users_db[sender_email].1 == old(users_db[sender_email].1) &&
      users_db[receiver_email].1 == old(users_db[receiver_email].1) &&
      
      users_db[sender_email].3 == old(users_db[sender_email].3) &&
      users_db[receiver_email].3 == old(users_db[receiver_email].3) &&
    
      users_db[sender_email].4 == old(users_db[sender_email].4) &&
      users_db[receiver_email].4 == old(users_db[receiver_email].4) &&
      
      logged_in_users == old(logged_in_users)
  ensures
    // Failure case - no changes made
    status == TransferFailure ==>
      users_db == old(users_db) &&
      logged_in_users == old(logged_in_users)
{
  
  var sender_data := users_db[sender_email];
  var sender_name := sender_data.0;
  var sender_pin := sender_data.1;
  var sender_balance := sender_data.2;
  var sender_locked := sender_data.3;
  var sender_attempts := sender_data.4;
  
  var receiver_data := users_db[receiver_email];
  var receiver_name := receiver_data.0;
  var receiver_pin := receiver_data.1;
  var receiver_balance := receiver_data.2;
  var receiver_locked := receiver_data.3;
  var receiver_attempts := receiver_data.4;
  
  var new_sender_balance := sender_balance - amount;
  var new_receiver_balance := receiver_balance + amount;
  
  users_db := users_db[sender_email := (sender_name, sender_pin, new_sender_balance, sender_locked, sender_attempts)];
  users_db := users_db[receiver_email := (receiver_name, receiver_pin, new_receiver_balance, receiver_locked, receiver_attempts)];

  status := TransferSuccess;
}


}