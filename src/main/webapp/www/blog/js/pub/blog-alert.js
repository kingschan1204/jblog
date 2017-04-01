var blogAlert = {
    dialog: null,
    alert: function (msg) {
        swal({type: 'warning', text: msg, timer: 2000});
    },
    confirm: function (msg, funcallback) {
        swal({
            //title: '是否继续',
            text: msg,
            type: 'question',
            showCancelButton: true,
            confirmButtonColor: '#42c02e',
            cancelButtonColor: '#d33',
            confirmButtonText: '是',
            cancelButtonText: '否'
        }).then(funcallback)
    },
    error:function(title,text){
        swal(
            title, text, 'error'
        )
    },
    success:function(title,text){
        swal(
            title, text, 'success'
        )
    },
    success:function(title,text,funcallback){
        swal({
            title: title,
            text: text,
            type: 'success'
        }).then(
            funcallback
        )
    },
    /**
     *
     * @param title
     * @param inputType text, email, password, number, tel, range, textarea, select, radio, checkbox, file and url.
     * @param inputVal 默认值
     * @param callback
     */
    prompt: function (title,inputType,inputVal,callback) {
        swal({
            title: title,
            input: inputType,
            inputValue:inputVal,
            showCancelButton: true,
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            confirmButtonColor: '#42c02e',
            cancelButtonColor: '#d33',
            showLoaderOnConfirm: true,
            //preConfirm: callback,
            allowOutsideClick: false
        }).then(callback);
    }, showLoading: function (msg) {
        swal({
            title: '',
            text: msg,
            width:'300px',
            imageUrl: '/www/showloading/loading.gif',
            showConfirmButton:false,
            showLoaderOnConfirm:true,
            allowOutsideClick:false
        })
       /* blogAlert.dialog = bootbox.dialog({
            message: '<p class="text-center"><img src="/www/showloading/loading.gif"/>&nbsp;' + msg + '</p>',
            size: 'small',
            closeButton: false
        });*/
    },
    closeLoading: function () {
       /* if (null == blogAlert.dialog)return;
        blogAlert.dialog.modal('hide');*/
        swal.close();
    },
    /**
     *
     * @param message
     * @param modeltype warning danger success
     */
    notify: function (message, modeltype) {
        $.notify({
                title: '<strong>提示</strong>',
                message: message
            }, {
                type: modeltype,
                placement: {from: 'top', align: 'center'},
                animate: {
                    enter: 'animated bounceInDown',
                    exit: 'animated bounceOutUp'
                }
            }
        );
    }
};