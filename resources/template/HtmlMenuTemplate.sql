#foreach($item in $!{tableList})
${item.businessName} /${item.pathName}/${item.className}List.html
#end


#set ($i=0)
#foreach($item in $!{tableList})
${item.businessName}-列表 /${item.pathName}/${item.className}List.html
${item.businessName}-新增 /${item.pathName}/${item.className}Add.html
${item.businessName}-修订 /${item.pathName}/${item.className}Edit.html
#end