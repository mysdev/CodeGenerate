#foreach($item in $!{tableList})
${item.businessName} /${item.pathName}/${item.className}List.html
#end


#set ($i=0)
#foreach($item in $!{tableList})
{
	"menuName": "${item.businessName}",
	"url": "${webPackage}/${item.className}List.html",
	"menuflag": "${item.className}list"
},
#end