#set ($i=0)
#foreach($item in $!{tableList})
INSERT INTO operation (`app_code`, `name`, `type`, `url`, `level`, `flag`, `is_show`, `tip`, `gmt_create`, `create_id`, `gmt_modified`, `modified_id`) VALUES ('os', '${item.businessName}-管理', 0, '/${item.pathName}/${item.className}List.html', 0, 1, 0, '${item.businessName}-管理', now(), 1, now(), 1);
#end