#set ($i=0)
#foreach($item in $!{tableList})
INSERT INTO ts_resource_info (`title`, `type`, `url`, `method`, `image`, `parent_id`, `is_display`, `sequence`, `enabled`, `note`, `created_by`) VALUES ('${item.businessName}-列表', 'HTML', '/${item.pathName}/${item.className}List.html', 'POST', NULL, NULL, 0, NULL, '0', '${item.businessName}-列表', 'admin');
INSERT INTO ts_resource_info (`title`, `type`, `url`, `method`, `image`, `parent_id`, `is_display`, `sequence`, `enabled`, `note`, `created_by`) VALUES ('${item.businessName}-新增', 'HTML', '/${item.pathName}/${item.className}Add.html', 'PUT', NULL, NULL, 0, NULL, '0', '${item.businessName}-新增', 'admin');
INSERT INTO ts_resource_info (`title`, `type`, `url`, `method`, `image`, `parent_id`, `is_display`, `sequence`, `enabled`, `note`, `created_by`) VALUES ('${item.businessName}-修订', 'HTML', '/${item.pathName}/${item.className}Edit.html', 'DELETE', NULL, NULL, 0, NULL, '0', '${item.businessName}-修订', 'admin');
#end