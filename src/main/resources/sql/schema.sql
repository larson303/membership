CREATE SCHEMA IF NOT EXISTS manage;

USE membership;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    user_id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    password        VARCHAR(100) NOT NULL,
    group_name      VARCHAR(50) NOT NULL,
    address         VARCHAR(255) DEFAULT NULL,
    city            VARCHAR(50) DEFAULT NULL,
    state           VARCHAR(50) DEFAULT NULL,
    zip             VARCHAR(10) DEFAULT NULL,
    phone           VARCHAR(20) DEFAULT NULL,
    title           VARCHAR(50) DEFAULT NULL,
    bio             VARCHAR(255) DEFAULT NULL,
    enabled         BOOLEAN DEFAULT FALSE,
    non_locked      BOOLEAN DEFAULT FALSE,
    using_mfa       BOOLEAN DEFAULT FALSE,
    image_url       VARCHAR(255) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    create_ts       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_ts       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    CONSTRAINT users_email_uq UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
    role_id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    role_name           VARCHAR(50) NOT NULL,
    role_permissions    VARCHAR(255) NOT NULL, -- user:read,user:write,admin:read,admin:write
    PRIMARY KEY (role_id),
    CONSTRAINT roles_role_name_uq UNIQUE (role_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles (
    user_role_id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id             BIGINT UNSIGNED NOT NULL,
    role_id             BIGINT UNSIGNED NOT NULL,
    create_ts           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_ts           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT user_roles_user_id_uq UNIQUE (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS events;
CREATE TABLE events (
    event_id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    event_type          VARCHAR(50) NOT NULL CHECK(event_type IN ('LOGIN_ATTEMPT', 'LOGIN_ATTEMPT_FAILURE', 'LOGIN_ATTEMPT_SUCCESS', 'LOGOUT', 'PROFILE_UPDATE', 'PROFILE_PICTURE_UPDATE', 'ROLE_UPDATE', 'ACCOUNT_SETTINGS_UPDATE', 'PASSWORD_UPDATE', 'MFA_UPDATE')),
    event_description   VARCHAR(255) NOT NULL,
    PRIMARY KEY (event_id),
    CONSTRAINT events_event_type_uq UNIQUE(event_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS user_events;
CREATE TABLE user_events (
    user_event_id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id             BIGINT UNSIGNED NOT NULL,
    event_id            BIGINT UNSIGNED NOT NULL,
    device_id           VARCHAR(100) NOT NULL,
    ip_address          VARCHAR(100) NOT NULL,
    create_ts           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_ts           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_event_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE RESTRICT ON UPDATE CASCADE
    -- vs  FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS account_verifications;
CREATE TABLE account_verification (
    account_verification_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id                 BIGINT UNSIGNED NOT NULL,
    verification_url        VARCHAR(255) NOT NULL,
    -- reset_url               VARCHAR(255) NULL,
    -- reset_ts                DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    create_ts               DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (account_verification_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT account_verification_user_id_uq UNIQUE (user_id),
    CONSTRAINT account_verification_verification_url_uq UNIQUE (verification_url)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- DROP TABLE IF EXISTS AccountVerifications;
-- Create Table AccountVerification ( 
--     account_verification_id     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
--     user_id                     BIGINT UNSIGNED NOT NULL,
--     verification_url            VARCHAR(255) NOT NULL,
--     -- date                     DATETIME NOT NULL,
--     PRIMARY KEY (account_verification_id),
--     FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
--     CONSTRAINT AccountVerification_user_id_uq UNIQUE (user_id),
--     CONSTRAINT AccountVerification_verification_url_uq UNIQUE (verification_url)
-- );

DROP TABLE IF EXISTS password_verifications;
Create Table password_verifications ( 
    password_verification_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id                     BIGINT UNSIGNED NOT NULL,
    verification_url            VARCHAR(255) NOT NULL,
    expiration_date             DATETIME NOT NULL,
    PRIMARY KEY (password_verification_id),
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT PasswordVerification_user_id_uq UNIQUE (user_id),
    CONSTRAINT PasswordVerification_verification_url_uq UNIQUE (verification_url)
);

-- MFA Verification
-- 2:30
DROP TABLE IF EXISTS mfa_verifications;
CREATE TABLE mfa_verification (
    mfa_verification_id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id                         BIGINT UNSIGNED NOT NULL,
    mfa_verification_token          VARCHAR(255) NOT NULL,
    mfa_verification_expiration     DATETIME NOT NULL,
    PRIMARY KEY (mfa_verification_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT mfa_verification_user_id_uq UNIQUE (user_id),
    CONSTRAINT mfa_verification_verification_url_uq UNIQUE (mfa_verification_token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



