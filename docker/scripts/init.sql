-- CREATE USER market_owner WITH PASSWORD 'market_owner';
CREATE USER market_update WITH PASSWORD 'market_update';
CREATE USER market_select WITH PASSWORD 'market_select';
CREATE DATABASE market;
GRANT ALL PRIVILEGES ON DATABASE market TO market_owner;
