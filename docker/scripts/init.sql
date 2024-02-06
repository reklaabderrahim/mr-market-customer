-- CREATE USER market_owner PASSWORD 'market_owner';
-- CREATE DATABASE customer;
-- CREATE DATABASE security;
-- CREATE DATABASE product;
-- GRANT ALL PRIVILEGES ON DATABASE customer TO market_owner;
-- GRANT ALL PRIVILEGES ON DATABASE security TO market_owner;
-- GRANT ALL PRIVILEGES ON DATABASE product TO market_owner;
--
-- ---
-- CREATE USER market_owner PASSWORD 'market_owner';
-- CREATE USER market_select PASSWORD 'market_select';
-- CREATE USER market_update PASSWORD 'market_update';
-- CREATE DATABASE market;
-- GRANT ALL PRIVILEGES ON DATABASE market TO market_owner;
--

CREATE USER market_owner WITH PASSWORD 'market_owner';
CREATE USER market_update WITH PASSWORD 'market_update';
CREATE USER market_select WITH PASSWORD 'market_select';
CREATE DATABASE market;
GRANT ALL PRIVILEGES ON DATABASE market TO market_owner;

