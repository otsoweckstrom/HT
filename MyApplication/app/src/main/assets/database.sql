create TABLE "bank" (
        "bankID" INTEGER NOT NULL PRIMARY KEY DEFAULT 1);

create TABLE "userAccount" (
        "userID"        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
        "loginName"     TEXT NOT NULL UNIQUE,
        "loginPassword" TEXT NOT NULL,
        CHECK (length(loginPassword)> 5)
);

create TABLE "debitAccount" (
        "accountNumber" TEXT NOT NULL UNIQUE,
        "userID"        INTEGER NOT NULL,
        "balance"       REAL DEFAULT 0,
        PRIMARY KEY("accountNumber"),
        FOREIGN KEY("userID") REFERENCES userAccount(userID) ON DELETE CASCADE
);
create TABLE "savingsAccount" (
        "accountNumber" TEXT NOT NULL UNIQUE,
        "userID"        INTEGER NOT NULL,
        "balance"       REAL DEFAULT 0,
        "savingsPercentage"     REAL NOT NULL,
        PRIMARY KEY("accountNumber"),
        FOREIGN KEY("userID") REFERENCES userAccount(userID) ON DELETE CASCADE
);
create TABLE "creditAccount" (
        "accountNumber" TEXT NOT NULL UNIQUE,
        "userID"        INTEGER NOT NULL,
        "balance"       REAL DEFAULT 0,
        "creditAmount"  REAL NOT NULL,
        PRIMARY KEY("accountNumber"),
        FOREIGN KEY("userID") REFERENCES userAccount(userID) ON DELETE CASCADE
);


create TABLE "debitCard" (
        "cardID"        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
        "cardNumber"     TEXT NOT NULL UNIQUE,
        "accountNumber" TEXT NOT NULL,
        FOREIGN KEY("accountNumber") REFERENCES debitAccount(accountNumber) ON DELETE CASCADE
);
create TABLE "creditCard" (
        "cardID"        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
        "cardNumber"     TEXT NOT NULL UNIQUE,
        "accountNumber" TEXT NOT NULL,
        FOREIGN KEY("accountNumber") REFERENCES creditAccount(accountNumber) ON DELETE CASCADE
);



