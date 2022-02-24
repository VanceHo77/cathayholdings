create table currency (
    id identity primary key,
    code char(3) not null,
    name varchar(30) not null,

    is_deleted bool not null,
    created_time datetime not null,
    updated_time datetime,
    deleted_time datetime
);
create index idx_currency_code on currency(code);
