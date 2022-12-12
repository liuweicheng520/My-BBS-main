layui.define(["i18n"], function (exports) {

    var i18n = layui.i18n;

    var i18np = {};

    i18np.loadProperties = function () {


        i18n.properties({
            name: 'i18n',
            path: '/', // 资源文件所在目录路径
            mode: "map", // 模式：变量或 Map
            language: "en", // 对应的语言
            cache: false,
            encoding: 'UTF-8',
            callback: function () {
                //这里是我通过对标签添加选择器来统一管理需要配置的地方
                // $(".layui-select-tips").html(i18n.prop("pla-select"));
                try {
                    //初始化页面元素
                    $('[data-i18n-placeholder]').each(function () {
                        $(this).attr('placeholder', i18n.prop($(this).data('i18n-placeholder')));
                    });
                    $('[data-i18n-text]').each(function () {
                        //如果text里面还有html需要过滤掉
                        var html = $(this).html();
                        var reg = /<(.*)>/;
                        if (reg.test(html)) {
                            var htmlValue = reg.exec(html)[0];
                            $(this).html(htmlValue + i18n.prop($(this).data('i18n-text')));
                        }
                        else {
                            $(this).text(i18n.prop($(this).data('i18n-text')));
                        }
                    });
                    $('[data-i18n-value]').each(function () {
                        $(this).val(i18n.prop($(this).data('i18n-value')));
                    });
                }
                catch (ex) { }
            },
        });
    };

    i18np.prop = function (i18nKey) {
        try {
            return i18n.prop(i18nKey);
        } catch (ex) {
            return i18nKey;
        }
    };

    exports("i18np", i18np);
});
