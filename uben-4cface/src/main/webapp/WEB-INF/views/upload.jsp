<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    $(function () {
        $('#uploadForm').form({
            url: '${url}',
            onSubmit: function () {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success: function (result) {
                progressClose();
                //console.log(JSON.stringify(result));
                result = $.parseJSON(result);
                if (result.success) {
                    var o = result.obj;
                    //console.log(JSON.stringify(o));
                    if (o) {
                        if (typeof(o) == 'string') o = $.parseJSON(o);
                        var total = o.total;
                        var effect = o.effect;
                        console.log("total:" + total + ";effect:" + effect);
                        if (total >= 0 && effect >= 0) {
                            parent.$.messager.alert('提示', '导入完成:总计' + total + ',成功:' + effect, 'info');
                        }
                    }
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="uploadForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>文件</td>
                    <td>
                        <input name="file" class="easyui-filebox" placeholder="选择文件..."
                               data-options="buttonText: '选择文件',required:true,prompt:'未选择文件'" style="width:80%"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        点击<a href="${ctx}/template/${templateName}.xls"><font color="red">>>这里<<</font></a>下载模板
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>