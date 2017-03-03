var blogControlPanel={
    /**
     *根据文本内容选中菜单
     * @param text
     */
    activePanelByText:function(text){
        $(".nav-folder").each(function(){
            var kw=$(this).text().trim();
            if(kw==text){
                $(this).addClass("nav-active");
            }
        })
    }
};