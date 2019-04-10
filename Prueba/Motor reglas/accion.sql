INSERT INTO motor.accion (id,esta_eliminado,fecha_creacion,fecha_modificacion,usuario_creacion,usuario_modificacion,campo_destino,campo_origen,nombre,obj_destino_id,obj_origen_id,tipo_cotejo_id,regla_fk) VALUES 
(10,0,'2018-11-16 11:07:18.000','2018-11-16 11:07:17.000',NULL,NULL,'reglaAplica',NULL,'accion test1',1,NULL,1,NULL)
,(11,0,'2018-11-16 11:07:18.000','2018-11-16 11:07:17.000',NULL,NULL,'reglaAplicaIgual','idServiPago','accion test2',1,1,2,1)
,(12,0,'2018-11-16 11:07:18.000','2018-11-16 11:07:17.000',NULL,NULL,'test','idServiPago','accion test3',2,1,2,NULL)
;