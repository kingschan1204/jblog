/**
 * Created by kingschan on 2017/3/13.
 */

var article = {
    /**
     * 删除文章
     * @param id
     */
    delArticle: function (id) {
        var lis = new Array();
        if (id != '' && id.length == 32) {
            lis.push(id);
        } else {
            //选择模式
            var chks = $("input[name='ar_chk']:checked");
            if (chks.length == 0) {
                blogAlert.alert("请选择要删除的文章", "red");
                return;
            }
            for (var i = 0; i < chks.length; i++) {
                lis.push(chks[i].value);
            }
        }
        blogAlert.confirm("你确定要删除选中记录吗？", function (result) {
            if (result) {
                blogAlert.showLoading("正在处理请稍后...");
                $.ajax({
                    type: "post",
                    url: "/admin/del_article.do",
                    data: {ids: lis},
                    success: function (msg) {
                        blogAlert.closeLoading();
                        bootbox.alert(msg);
                        if (msg.indexOf("成功") != -1) {
                            $("#sform").submit();
                        }

                    }
                });
            }
        });
    }
    /**
     * 删除标签
     * @param labid
     */
    , dellable: function (labid) {
        blogAlert.confirm(
            "是否删除此标签?",
            function (result) {
                if (result) {
                    $.ajax({
                        type: "get",
                        url: "/admin/del_article_lable.do",
                        data: {id: labid},
                        success: function (msg) {
                            if (msg == "success") {
                                location.reload();
                            } else {
                                alert(msg);
                            }
                        }
                    });
                }
            }
        )
    }
    /**
     * 取消置顶
     */
    , canclefixedTop: function (id) {
        blogAlert.showLoading("正中处理");
        var array = new Array();
        array.push(id);
        $.ajax({
            type: "post",
            url: "/admin/cancleFixedTop.do",
            data: {ids: array},
            success: function (msg) {
                blogAlert.closeLoading();
                if (msg == "success") {
                    location.reload();
                } else {
                    bootbox.alert(msg);
                }

            }
        });
    }

    /**
     * 改变文章类型
     */
    , updateCategory: function (data) {
        var chks = $("input[name='ar_chk']:checked");
        if (chks.length == 0) {
            blogAlert.error("请选择要分类的文章");
            return;
        }
        swal({
            title: '请选择文章类型',
            input: 'select',
            inputOptions: data,
            showCancelButton: true,
            confirmButtonColor: '#42c02e',
            cancelButtonColor: '#d33',
            confirmButtonText: '确定',
            cancelButtonText: '取消'
        }).then(function (result) {
            blogAlert.showLoading("正在处理,请稍后.");
            var lis = new Array();
            for (var i = 0; i < chks.length; i++) {
                lis.push(chks[i].value);
            }
            $.ajax({
                type: "post",
                url: "/admin/update_articletype.do",
                data: {ids: lis, category: result},
                success: function (msg) {
                    blogAlert.closeLoading();
                    if (msg == "success") {
                        location.reload();
                    } else {
                        blogAlert.alert(msg, "blue");
                    }

                }
            });
        })
    }
    /**
     * 置顶
     */
    , fixedTop: function (id) {
        blogAlert.showLoading("正中处理");
        var array = new Array();
        array.push(id);
        $.ajax({
            type: "post",
            url: "/admin/fixedTop.do",
            data: {ids: array},
            success: function (msg) {
                blogAlert.closeLoading();
                if (msg == "success") {
                    location.reload();
                } else {
                    bootbox.alert(msg);
                }

            }
        });
    }
    /**
     * 抓取图片
     */
    , crawImg: function (id) {
        blogAlert.showLoading("正中处理");
        $.ajax({
            type: "post",
            url: "/admin/outsideImgResTransformation.do",
            data: {id: id},
            success: function (msg) {
                blogAlert.closeLoading();
                if (msg == "success") {
                    blogAlert.success("操作成功");
                } else {
                    blogAlert.error(msg);
                }

            }
        });
    }
};

function allchk(obj) {
    var chk = obj.checked;
    if (chk) {
        $("input[name='ar_chk']").prop("checked", true);
    } else {
        $("input[name='ar_chk']").prop("checked", false);
    }
}
function rowclick(row) {
    var checked = $(row.cells[0]).children(":checkbox").prop("checked");
    $(row.cells[0]).children(":checkbox").prop("checked", checked ? false : true);
}