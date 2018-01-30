<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<title>${businessName}</title>
		<link rel="stylesheet" type="text/css" href="../js/artdialog/ui-dialog.css" />
		<link rel="stylesheet" type="text/css" href="../css/jqPaginator.css" />
		<link rel="stylesheet" type="text/css" href="../css/skin/icon/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="../css/skin/default/style.css" />
		<script type="text/javascript" charset="utf-8" src="../js/jquery/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/artdialog/dialog-plus-min.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/laymain.js"></script>		
		<script type="text/javascript" charset="utf-8" src="../js/jquery/knockout-3.4.2.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jquery/jquery.jsonp.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jquery/jqPaginator.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jquery/moment.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jl/jquery.ajax.jl.js"></script>	
	</head>

	<body class="mainbody">
		<!--导航栏-->
		<div class="location">
			<a href="javascript:history.back(-1);" class="back"><i class="iconfont icon-up"></i><span>返回上一页</span></a>
			<a href="../center.html" class="home"><i class="iconfont icon-home"></i><span>首页</span></a>
			<i class="arrow iconfont icon-arrow-right"></i>
			<span>${businessName}</span>
			<i class="arrow iconfont icon-arrow-right"></i>
			<span>列表</span>
		</div>
		<!--/导航栏-->

		<!--工具栏-->
		<div id="floatHead" class="toolbar-wrap">
			<div class="toolbar">
				<div class="box-wrap">
					<a class="menu-btn"><i class="iconfont icon-more"></i></a>
					<div class="l-list">
						<ul class="icon-list">
							<li>
								<a href="#" data-bind="click:$root.add"><i class="iconfont icon-close"></i><span>新增</span></a>
							</li>
							<li>
								<a href="javascript:;" onclick="checkAll(this);"><i class="iconfont icon-check"></i><span>全选</span></a>
							</li>
							<li>
								<a data-bind="click:$root.deletes" id="lbtnDelete" href="#"><i class="iconfont icon-delete"></i><span>删除</span></a>
							</li>
						</ul>
					</div>
					<div class="r-list">
						<input name="txtKeywords" type="text" id="txtKeywords" class="keyword" />
						<a id="lbtnSearch" class="btn-search" href="#" data-bind="click:$root.search"><i class="iconfont icon-search"></i></a>
					</div>
				</div>
			</div>
		</div>
		<!--/工具栏-->

		<!--列表-->
		<div class="table-container">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
				<thead>
					<tr>
						<th width="4%">选择</th>
#set($i=0)
#foreach($item in $!{columnList})
#if($i!=0)	
						<th align="left">${item.classLable}</th>
#end
#set($i=$i+1)
#end
					</tr>
				</thead>
				<tbody data-bind="foreach: { data: ${entityName}List, as: 'obj' }">
					<tr>
						<td align="center">
							<span class="checkall" style="vertical-align:middle;"><input id="chkid" type="checkbox" name="chkid"  data-bind="attr:{data: obj.${keyColumn.classParam}}" /></span>
						</td>
#set($i=0)
#foreach($item in $!{columnList})
#if($i!=0)
						<td data-bind="text: obj.$!item.classParam"></td>
#end
#set($i=$i+1)
#end
						<td align="center">
							<a href="#" data-bind="click:$root.modify" >修改</a>&nbsp;
							<a href="#" data-bind="click:$root.delete" >删除</a>
						</td>
					</tr>
				</tbody>
			</table>

		</div>
		<!--/列表-->

		<!--内容底部-->
		<div class="line20"></div>
		<div class="pagelist">
			<ul class="pagination" id="pagination"></ul>
		</div>
		<div id="tx"></div>
		<!--/内容底部-->

		</form>
		<!--列表数据绑定开始-->
		<script src="./model/${entityName}List.js"></script>	
		<!--列表数据绑定结束-->
	</body>

</html>