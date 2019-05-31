#set ($i=0)
#foreach($item in $!{tableList})
INSERT INTO api_atom (`app_code`, `api_name`, `url`, `method`, `gmt_create`, `create_id`, `gmt_modified`, `modified_id`) VALUES ('os', '${item.businessName}-新增', '/${item.pathName}', 'POST', now(), 1, now(), 1 );
INSERT INTO api_atom (`app_code`, `api_name`, `url`, `method`, `gmt_create`, `create_id`, `gmt_modified`, `modified_id`) VALUES ('os', '${item.businessName}-修订', '/${item.pathName}/{}', 'PUT', now(), 1, now(), 1 );
INSERT INTO api_atom (`app_code`, `api_name`, `url`, `method`, `gmt_create`, `create_id`, `gmt_modified`, `modified_id`) VALUES ('os', '${item.businessName}-删除', '/${item.pathName}/{}', 'DELETE', now(), 1, now(), 1 );
INSERT INTO api_atom (`app_code`, `api_name`, `url`, `method`, `gmt_create`, `create_id`, `gmt_modified`, `modified_id`) VALUES ('os', '${item.businessName}-标识查询', '/${item.pathName}/{}', 'GET', now(), 1, now(), 1 );
INSERT INTO api_atom (`app_code`, `api_name`, `url`, `method`, `gmt_create`, `create_id`, `gmt_modified`, `modified_id`) VALUES ('os', '${item.businessName}-不分页列表', '/${item.pathName}', 'GET', now(), 1, now(), 1 );
INSERT INTO api_atom (`app_code`, `api_name`, `url`, `method`, `gmt_create`, `create_id`, `gmt_modified`, `modified_id`) VALUES ('os', '${item.businessName}-分页列表', '/${item.pathName}s', 'GET', now(), 1, now(), 1 );
#end