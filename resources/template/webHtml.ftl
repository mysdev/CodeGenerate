<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>编辑${businessName}</title>
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<link rel="stylesheet" type="text/css" href="../js/artdialog/ui-dialog.css" />
		<link rel="stylesheet" type="text/css" href="../css/skin/icon/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="../css/skin/default/style.css" />
		<link rel="stylesheet" type="text/css" href="../js/flatpickr/flatpickr.min.css" />
		<script type="text/javascript" charset="utf-8" src="../js/jquery/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jquery/knockout-3.4.2.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jquery/Validform_v5.3.2_min.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/artdialog/dialog-plus-min.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/flatpickr/flatpickr.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/laymain.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jl/jquery.ajax.jl.js"></script>	
		<script type="text/javascript">
			$(function() {
				//初始化表单验证
				$("#form1").initValidform();
#foreach($item in $!{columnList})
#if($!item.classType == 'Date' && !($!{item.isInsertColumn} || $!{item.isUpdateColumn}))
				$("#${item.classParam}").flatpickr();
#end
#end
			});
		</script>
	</head>

	<body class="mainbody">
		<form method="post" action="${className}.html?action=Add" id="form1">
			<!--导航栏-->
			<div class="location">
				<a href="${className}List.html" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
				<a href="../center.html"><i class="iconfont icon-home"></i><span>首页</span></a>
				<i class="arrow iconfont icon-arrow-right"></i>
				<a href="attribute_field_list.aspx"><span>${businessName}</span></a>
				<i class="arrow iconfont icon-arrow-right"></i>
				<span>编辑${businessName}</span>
			</div>
			<div class="line10"></div>
			<!--/导航栏-->

			<!--内容-->
			<div id="floatHead" class="content-tab-wrap">
				<div class="content-tab">
					<div class="content-tab-ul-wrap">
						<ul>
							<li>
								<a class="selected" href="javascript:;">编辑${businessName}</a>
							</li>
						</ul>
					</div>
				</div>
			</div>

			<div class="tab-content">
#set ($i=0)
#foreach($item in $!{columnList})
#if($i!=0)
#if(!${item.isBaseColumn})
#if($!item.classType == 'Integer')
				<dl>
					<dt>${item.classLable}</dt>
					<dd><input name="${item.classParam}" type="text" id="${item.classParam}" class="input" datatype="n" sucmsg=" " data-bind="value:${item.classParam}" /> #if(!${item.columnNullable})<span class="Validform_checktip">*</span>#end </dd>
				</dl>
#elseif($!item.classType == 'Date')
				<dl>
					<dt>${item.classLable}</dt>
					<dd>
						<input name="${item.classParam}" type="text" id="${item.classParam}" data-bind="value:${item.classParam}" class="input rule-date-input flatpickr" data-enable-time="true" data-no-calendar="true" data-time_24hr="true" datatype="/^(\d{1,2}:){1}\d{1,2}$/" errormsg="请选择正确的时间" sucmsg=" " />
#if(!${item.columnNullable})<span class="Validform_checktip">*选择${item.classLable}</span>#end
					</dd>
				</dl>
#else
				<dl>
					<dt>${item.classLable}</dt>
					<dd><input name="${item.classParam}" type="text" id="${item.classParam}" class="input normal" datatype="*3-20" sucmsg=" " data-bind="value:${item.classParam}" /> #if(!${item.columnNullable})<span class="Validform_checktip">*</span>#end </dd>
				</dl>
#end
#end
#end
#set($i=$i+1)
#end				
			</div>
			<!--/内容-->

			<!--工具栏-->
			<div class="page-footer">
				<div class="btn-wrap">
					<input type="button" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" data-bind="click:$root.Commit" />
					<input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);" />
				</div>
			</div>
			<!--/工具栏-->
		</form>
		<!--列表数据绑定开始-->
		<script src="./model/${className}.js"></script>		
		<!--列表数据绑定结束-->
	</body>

</html>