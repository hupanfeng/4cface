!(function (global) {
    var modules = {
        jquery: {
            js: 'lib/jquery-1.8.3.min.js'
        },
        easyui: {
            js: 'lib/easyui1.4.4/jquery.easyui.min.js',
            //css: ['lib/easyui1.4.4/themes/default/easyui.css'],
            dependencies: ['jquery']
        },
        easyui_locale: {
            js: 'lib/easyui1.4.4/locale/easyui-lang-zh_CN.js',
            dependencies: ['easyui']
        },
        extEasyUI: {
            js: 'lib/extEasyUI.js',
            dependencies: ['easyui']
        },
        extJquery: {
            js: 'lib/extJquery.js',
            dependencies: ['jquery', 'easyui']
        },
        cookie: {
            js: 'lib/jquery.cookie.js',
            dependencies: ['jquery']
        },
        store: {
            js: 'lib/store.js'
        },
        dic: {
            js: 'dic.js',
            dependencies: ['jquery'],
            cache: false
        },        
        base: {
            js: 'base.js',
            dependencies: ['jquery']
        },
        common: {
            js: 'common.js',
            dependencies: ['base','easyui_locale', 'extEasyUI', 'extJquery', 'cookie', 'store', 'dic']
        },
        "easyui.print": {
            js: 'lib/easyui.print.js',
            dependencies: ['easyui']
        },
        portal: {
            js: 'lib/easyui1.4.4/jquery.portal.js',
            dependencies: ['easyui']
        },
        searchCustom: {
            js: 'searchCustom.js',
            dependencies: ['jquery', 'cookie']
        },
        toast: {
            js: 'lib/toast/toastr.min.js',
            dependencies: ['jquery']
        },
        fancybox: {
            js: 'lib/fancybox/jquery.fancybox.pack.js',
            css: ['lib/fancybox/jquery.fancybox.css'],
            dependencies: ['jquery']
        },
        echarts: {
            js: 'lib/echarts3-0-2.min.js',
            dependencies: ['jquery']
        }
    };

    var queues = {};

    function loadJs(url, callback) {
        var done = false;
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.language = 'javascript';
        script.src = url;
        script.onload = script.onreadystatechange = function () {
            if (!done && (!script.readyState || script.readyState == 'loaded' || script.readyState == 'complete')) {
                done = true;
                script.onload = script.onreadystatechange = null;
                if (callback) {
                    callback.call(script);
                }
            }
        }
        document.getElementsByTagName("head")[0].appendChild(script);
    }

    function runJs(url, callback) {
        loadJs(url, function () {
            document.getElementsByTagName("head")[0].removeChild(this);
            if (callback) {
                callback();
            }
        });
    }

    function loadCss(url, callback) {
        var link = document.createElement('link');
        link.rel = 'stylesheet';
        link.type = 'text/css';
        link.media = 'screen';
        link.href = url;
        document.getElementsByTagName('head')[0].appendChild(link);
        if (callback) {
            callback.call(link);
        }
    }

    function loadSingle(name, callback) {
        queues[name] = 'loading';

        var module = modules[name];
        var jsStatus = 'loading';
        var cssStatus = (loader.css && module['css']) ? 'loading' : 'loaded';

        if (loader.css && module['css']) {
            var loadedCss = 0;
            for (var i = 0; i < module['css'].length; i++) {
                if (/^http/i.test(module['css'][i])) {
                    var url = module['css'][i];
                } else {
                    var url = loader.base + module['css'][i];
                }
                loadCss(url, function () {
                    ++loadedCss;
                    if (loadedCss == module['css'].length) {
                        cssStatus == 'loaded';
                        console.log(cssStatus);
                    }
                    if (jsStatus == 'loaded' && cssStatus == 'loaded') {
                        finish();
                    }
                });
            }
            cssStatus == 'loaded';
        }

        if (/^http/i.test(module['js'])) {
            var url = module['js'];
        } else {
            var url = loader.base + module['js'];
            if (undefined !== module['cache'] && !module['cache']) {
                url += "?" + Math.random();
            }
        }
        loadJs(url, function () {
            jsStatus = 'loaded';
            //if (jsStatus == 'loaded' && cssStatus == 'loaded') {
            if (jsStatus == 'loaded') {
                finish();
            }
        });

        function finish() {
            queues[name] = 'loaded';
            loader.onProgress(name);
            if (callback) {
                callback();
            }
        }
    }

    function loadModule(name, callback) {
        var mm = [];
        var doLoad = false;

        if (typeof name == 'string') {
            add(name);
        } else {
            for (var i = 0; i < name.length; i++) {
                add(name[i]);
            }
        }

        function add(name) {
            if (!modules[name]) return;
            var d = modules[name]['dependencies'];
            if (d) {
                for (var i = 0; i < d.length; i++) {
                    add(d[i]);
                }
            }
            mm.push(name);
        }

        function finish() {
            if (callback) {
                callback();
            }
            loader.onLoad(name);
        }

        var time = 0;

        function loadMm() {
            if (mm.length) {
                var m = mm[0];	// the first module
                if (!queues[m]) {
                    doLoad = true;
                    loadSingle(m, function () {
                        mm.shift();
                        loadMm();
                    });
                } else if (queues[m] == 'loaded') {
                    mm.shift();
                    loadMm();
                } else {
                    if (time < loader.timeout) {
                        time += 10;
                        setTimeout(arguments.callee, 10);
                    }
                }
            } else {
                finish();
            }
        }

        loadMm();
    }

    var loader = {
        modules: modules,
        base: '.',
        theme: 'default',
        css: true,
        locale: null,
        timeout: 2000,

        load: function (name, callback) {
            if (/\.css$/i.test(name)) {
                if (/^http/i.test(name)) {
                    loadCss(name, callback);
                } else {
                    loadCss(loader.base + name, callback);
                }
            } else if (/\.js$/i.test(name)) {
                if (/^http/i.test(name)) {
                    loadJs(name, callback);
                } else {
                    loadJs(loader.base + name, callback);
                }
            } else {
                loadModule(name, callback);
            }
        },

        onProgress: function (name) {
        },
        onLoad: function (name) {
        }
    };

    var scripts = document.getElementsByTagName('script');
    for (var i = 0; i < scripts.length; i++) {
        var src = scripts[i].src;
        if (!src) continue;
        var m = src.match(/loader\.js(\W|$)/i);
        if (m) {
            loader.base = src.substring(0, m.index);
        }
    }
    // 解决IE8不能使用forEach的bug
    if ( !Array.prototype.forEach ) {
        Array.prototype.forEach = function forEach( callback, thisArg ) {
            var T, k;

            if ( this == null ) {
                throw new TypeError( "this is null or not defined" );
            }
            var O = Object(this);
            var len = O.length >>> 0;
            if ( typeof callback !== "function" ) {
                throw new TypeError( callback + " is not a function" );
            }
            if ( arguments.length > 1 ) {
                T = thisArg;
            }
            k = 0;

            while( k < len ) {

                var kValue;
                if ( k in O ) {

                    kValue = O[ k ];
                    callback.call( T, kValue, k, O );
                }
                k++;
            }
        };
    }
    global.loader = loader;

    // if (window.jQuery) {
    //     jQuery(function () {
    //         loader.load('parser', function () {
    //             jQuery.parser.parse();
    //         });
    //     });
    // }
})(window);
