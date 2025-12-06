CREATE TABLE orders (
    id VARCHAR(36) PRIMARY KEY,
    merchant_id VARCHAR(36) NOT NULL,
    total_amount BIGINT NOT NULL,
    service_fee BIGINT NOT NULL,
    paid_amount BIGINT,
    currency VARCHAR(3) NOT NULL,
    currency_scale INT NOT NULL,
    status INT NOT NULL,
    payment_status INT NOT NULL,
    payment_mode INT,
    order_type INT NOT NULL,
    create_time TIMESTAMP NOT NULL,
    payment_time TIMESTAMP,
    payment_deadline TIMESTAMP,
    delivery_time TIMESTAMP,
    finish_time TIMESTAMP,
    payment_channel_id VARCHAR(255),
    payment_trade_no VARCHAR(255),
    timezone VARCHAR(50),
    reservation_time TIMESTAMP,
    table_no VARCHAR(50),
    dining_number INT,
    pickup_time TIMESTAMP,
    delivery_type INT
);

CREATE TABLE order_items (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL REFERENCES orders(id),
    product_id VARCHAR(36) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    unit_price BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_price BIGINT NOT NULL,
    currency VARCHAR(3) NOT NULL,
    currency_scale INT NOT NULL,
    payment_status INT
);

CREATE TABLE product_specifications (
    id VARCHAR(36) PRIMARY KEY,
    order_item_id VARCHAR(36) NOT NULL REFERENCES order_items(id),
    product_spec_id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    price BIGINT NOT NULL,
    currency VARCHAR(3) NOT NULL,
    currency_scale INT NOT NULL
);