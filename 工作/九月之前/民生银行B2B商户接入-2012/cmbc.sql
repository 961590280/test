drop table if exists cmbc_direct_pay_qequest;

/*==============================================================*/
/* Table: cmbc_direct_pay_qequest     支付记录表                          */
/*==============================================================*/
create table cmbc_direct_pay_qequest
(
   merchant_trnx_no     varchar(64) not null comment '商户交易序号',
   company_id           varchar(64) not null comment '企业id',
   trnx_code            varchar(100) not null comment '直接支付交易代码',
   payer_cust_no        varchar(10) not null comment '买方企业客户号',
   result_notify_url    varchar(200) not null comment '商户交易信息通知URL',
   trnx_amount          decimal(15,2) not null comment '交易金额',
   digest               varchar(200) comment '消息摘要',
   order_no             varchar(64) not null comment '订单编号',
   order_amt            decimal(15,2) not null comment '订单金额',
   order_date           varchar(50) not null comment '订单日期',
   oper_name            varchar(100) comment '下单员姓名',
   remark_entry         varchar(100) comment '交易说明性信息的集合',
   pid                  varchar(100) comment '产品代码',
   pn                   varchar(100) comment '产品名称',
   up                   varchar(100) comment '产品数量',
   des                  text comment '商品描述',
   now_date            datetime not null comment '当前时间';
   primary key (merchant_trnx_no)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists cmbc_pay_result;

/*==============================================================*/
/* Table: cmbc_pay_result    支付结果表                                   */
/*==============================================================*/
create table cmbc_pay_result
(
   result_id            int not null AUTO_INCREMENT comment '主键',
   merchant_trnx_no     varchar(64) not null   comment '商户交易编号',
   order_no             varchar(100) not null comment '订单编号',
   company_id           varchar(50) not null comment '企业id',
   trnx_type            varchar(50) not null comment '交易类型',
   trnx_create_time     varchar(50) not null comment '交易被创建的时间',
   trnx_date            varchar(50) comment '交易实际执行的日期',
   trnx_amount          decimal(15,2) not null comment '交易金额',
    pay_status           varchar(10) not null comment '交易状态',
   buyer_fee_amount     decimal(15,2) comment '交易对应的买方手续费',
   seller_fee_amount    decimal(15,2) comment '交易对应的卖方手续费',
   payer_amount         decimal(15,2) comment '转出金额',
   payee_amount         decimal(15,2) comment '转入金额',
   bank_fee_amount      decimal(15,2) comment '银行手续费收益金额',
   bourse_fee_amount    decimal(15,2) comment '商户手续费收益金额',
   redirect_url         varchar(200) comment '重新导向URL',
   primary key (result_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists cmbc_query_order;

/*==============================================================*/
/* Table: cmbc_query_order     查询订单记录表                                 */
/*==============================================================*/
create table cmbc_query_order
(
   order_id             int not null AUTO_INCREMENT comment '主键',
   merchant_trnx_no     varchar(64) not null comment '商户交易序号',
   company_id           varchar(64) not null comment '企业Id',
   trnx_code            varchar(100) not null comment '订单码',
   order_no             varchar(100) not null comment '要查询的订单号',
   now_date             datetime not null comment '当前时间',
   primary key (order_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists cmbc_query_tms;

/*==============================================================*/
/* Table: cmbc_query_tms        请求 交易记录表                              */
/*==============================================================*/
create table cmbc_query_tms
(
   tms_id               int not null AUTO_INCREMENT comment '主键',
   merchant_trnx_no     varchar(64) not null comment '商户交易序号',
   company_id           varchar(64) not null comment '企业id',
   trnx_code            varchar(20) not null comment '查询码',
   qry_trnx_no          varchar(100) not null comment '要查询的交易编号',
   now_date             datetime not null comment '当前时间',
   primary key (tms_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists cmbc_order_status;

/*==============================================================*/
/* Table: cmbc_order_status         订单状态表                            */
/*==============================================================*/
create table cmbc_order_status
(
   status_id            int not null AUTO_INCREMENT comment '主键',
   merchant_trnx_no     varchar(64) not null comment '商户交易序号',
   order_no             varchar(100) not null comment '要查询的订单号',
   order_status         varchar(10) not null comment '订单状态码',
   primary key (status_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists cmbc_result;

/*==============================================================*/
/* Table: cmbc_result        原始返回结果表                                   */
/*==============================================================*/
create table cmbc_result
(
   result_id            int not null AUTO_INCREMENT comment '主键',
   merchant_trnx_no     varchar(64) not null comment '商户交易序号',
   result_status        varchar(20) not null comment '返回的状态码',
   result_msg           varchar(300) not null comment '返回的信息',
   from_result          varchar(20) not null comment '结果来源',
   result_data          text not null comment '源数据',
   service_date			varchar(50) not null comment '服务器时间'
   primary key (result_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists cmbc_order_result;

/*==============================================================*/
/* Table: cmbc_order_result  查询订单结果表                                   */
/*==============================================================*/
create table cmbc_order_result
(
   result_id            int not null AUTO_INCREMENT comment '主键',
   merchant_trnx_no     varchar(64) not null comment '商户交易编号',
   company_id           varchar(50) not null comment '企业id',
   order_no             varchar(100) not null comment '订单编号',
   code                 varchar(50) not null comment '交易处理结果代码',
   message              varchar(200) comment '描述信息',
   trnx_code            varchar(50) comment '交易代码',
   order_amt            decimal(15,2) not null comment '订单金额',
   order_date           varchar(50) not null comment '订单日期时间',
   seller_bank          varchar(100) comment '卖方开户行',
   seller_bank_address  varchar(200) comment '卖方开户行地址',
   buyer_corporation_name varchar(50) comment '买方企业名称',
   seller_corporation_name varchar(50) comment '卖方企业名称',
   pay_type             varchar(10) not null comment '跨行标识',
   oper_name            varchar(50) comment '下单员姓名',
   pay_key                  varchar(20) comment '交易说明信息Key',
   pay_value                varchar(20) comment '交易说明信息Value',
   pid                  varchar(20) comment '产品代码',
   pn                   varchar(20) comment '产品名称',
   up                   varchar(20) comment '产品单价',
   qty                  varchar(20) comment '购买数量',
   des                  varchar(20) comment '商品描述',
   status               varchar(20) comment '订单状态',
   primary key (result_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists cmbc_down_order;

/*==============================================================*/
/* Table: cmbc_down_order       下载对帐单记录表                                */
/*==============================================================*/
create table cmbc_down_order
(
   down_id              int not null  AUTO_INCREMENT comment '主键',
   merchant_trnx_no     varchar(100) not null comment '商户交易序号',
   company_id           varchar(64) not null comment '企业id',
   trnx_code            varchar(20) not null comment '查询码',
   down_date            varchar(50) not null comment '要下载的对帐单日期',
   now_date             datetime  not null comment '当前时间',
   primary key (down_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists cmbc_company_pay;

/*==============================================================*/
/* Table: cmbc_company_pay      企业银行账号表                                */
/*==============================================================*/
create table cmbc_company_pay
(
   company_pay_id       int not null AUTO_INCREMENT comment '主键',
   company_id           varchar(64) not null comment '企业id',
   merchant_trnx_no     varchar(64) not null comment '商户交易编号',
   seller_bank          varchar(100) comment '卖方开户行',
   seller_bank_address  varchar(200) comment '卖方开户行地址',
   aayee_acct_no        varchar(100) not null comment '卖方企业帐号',
   payee_acct_Nname     varchar(100) not null comment '卖方企业帐户名称',
   pay_type             int not null comment '是否是民生银行',
   k_type                 int not null comment '客户类型',
   pay_company_no       varchar(50) not null comment '企业网银客户号',
   pay_name             varchar(50) comment '支付账号姓名',
   pay_idcard           varchar(30) comment '支付身份证号码',
   bank_type            int comment '银行类别',
   bank_mobile          varchar(20) comment '预留手机号码',
   bank_num             varchar(50) comment '银行账号',
   primary key (company_pay_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;







