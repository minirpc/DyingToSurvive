create database rpc_config DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;


CREATE TABLE `rpc_config`.`service_weight` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '服务权重id',
  `serviceName` VARCHAR(50) NOT NULL COMMENT '服务名称',
  `serviceInterface` VARCHAR(20) NOT NULL COMMENT '服务接口名称',
  `applicationName` VARCHAR(45) NOT NULL COMMENT '应用名称',
  `createdBy` VARCHAR(45) NOT NULL COMMENT '创建人',
  `updatedBy` VARCHAR(45) NOT NULL COMMENT '更新人',
  `weight` INT NOT NULL DEFAULT 100 COMMENT '权重',
  `status` VARCHAR(45) NOT NULL DEFAULT 'INACTIVE' COMMENT '权重状态',
  `is_valid` VARCHAR(1) NOT NULL DEFAULT 'Y' COMMENT '是否有效',
  `create_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB COMMENT='服务权重表';



