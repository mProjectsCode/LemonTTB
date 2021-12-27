(function(t){function e(e){for(var r,s,u=e[0],c=e[1],i=e[2],l=0,p=[];l<u.length;l++)s=u[l],Object.prototype.hasOwnProperty.call(o,s)&&o[s]&&p.push(o[s][0]),o[s]=0;for(r in c)Object.prototype.hasOwnProperty.call(c,r)&&(t[r]=c[r]);b&&b(e);while(p.length)p.shift()();return a.push.apply(a,i||[]),n()}function n(){for(var t,e=0;e<a.length;e++){for(var n=a[e],r=!0,s=1;s<n.length;s++){var c=n[s];0!==o[c]&&(r=!1)}r&&(a.splice(e--,1),t=u(u.s=n[0]))}return t}var r={},o={app:0},a=[];function s(t){return u.p+"js/"+({about:"about"}[t]||t)+"."+{about:"25fd2755"}[t]+".js"}function u(e){if(r[e])return r[e].exports;var n=r[e]={i:e,l:!1,exports:{}};return t[e].call(n.exports,n,n.exports,u),n.l=!0,n.exports}u.e=function(t){var e=[],n=o[t];if(0!==n)if(n)e.push(n[2]);else{var r=new Promise((function(e,r){n=o[t]=[e,r]}));e.push(n[2]=r);var a,c=document.createElement("script");c.charset="utf-8",c.timeout=120,u.nc&&c.setAttribute("nonce",u.nc),c.src=s(t);var i=new Error;a=function(e){c.onerror=c.onload=null,clearTimeout(l);var n=o[t];if(0!==n){if(n){var r=e&&("load"===e.type?"missing":e.type),a=e&&e.target&&e.target.src;i.message="Loading chunk "+t+" failed.\n("+r+": "+a+")",i.name="ChunkLoadError",i.type=r,i.request=a,n[1](i)}o[t]=void 0}};var l=setTimeout((function(){a({type:"timeout",target:c})}),12e4);c.onerror=c.onload=a,document.head.appendChild(c)}return Promise.all(e)},u.m=t,u.c=r,u.d=function(t,e,n){u.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},u.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},u.t=function(t,e){if(1&e&&(t=u(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(u.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var r in t)u.d(n,r,function(e){return t[e]}.bind(null,r));return n},u.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return u.d(e,"a",e),e},u.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},u.p="/",u.oe=function(t){throw console.error(t),t};var c=window["webpackJsonp"]=window["webpackJsonp"]||[],i=c.push.bind(c);c.push=e,c=c.slice();for(var l=0;l<c.length;l++)e(c[l]);var b=i;a.push([0,"chunk-vendors"]),n()})({0:function(t,e,n){t.exports=n("56d7")},"10bf":function(t,e,n){},"56d7":function(t,e,n){"use strict";n.r(e);n("e260"),n("e6cf"),n("cca6"),n("a79d");var r=n("7a23"),o={id:"app"},a={id:"main"},s=Object(r["i"])('<div class="p-5 bg-primary text-white text-center"><h1>LemonTTB</h1><p>LemonTTB is a Discord Bot designed to assist the GM during tabletop sessions, that use Discord for communication.</p></div><nav class="navbar navbar-expand-sm bg-dark navbar-dark"><div class="container-fluid"><ul class="navbar-nav"><li class="nav-item"><a class="nav-link" href="https://github.com/mProjectsCode/LemonTTB">GitHub</a></li><li class="nav-item"><a class="nav-link" href="#">About LemonTTB</a></li></ul></div></nav>',2),u=Object(r["h"])("footer",{class:"mt-5 p-4 bg-dark text-white text-center",style:{"z-index":"11"}},[Object(r["h"])("p",null,"LemonTTB - GPL-3.0 License")],-1);function c(t,e,n,c,i,l){var b=Object(r["C"])("router-view");return Object(r["w"])(),Object(r["g"])("div",o,[Object(r["h"])("div",a,[s,Object(r["k"])(b),u])])}var i=n("1da1"),l=(n("96cf"),n("d3b7"),n("b0c0"),{data:function(){return{source:null}},mounted:function(){this.subscribeToEvents()},computed:{botStatus:function(){return this.$store.getters.getBotStatus}},methods:{subscribeToEvents:function(){var t=this;return Object(i["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,fetch("api/events/unsubscribe");case 2:t.source=new EventSource("/api/events/subscribe"),t.source.onmessage=t.onEventMessage;case 4:case"end":return e.stop()}}),e)})))()},onEventMessage:function(t){var e=this;return Object(i["a"])(regeneratorRuntime.mark((function n(){var r;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:r=JSON.parse(t.data),console.log(r),e.toast.success(r.name+" status "+r.payload.response,{timeout:1e4});case 3:case"end":return n.stop()}}),n)})))()}}}),b=(n("840e"),n("6b0d")),p=n.n(b);const f=p()(l,[["render",c]]);var d=f,h=(n("3ca3"),n("ddb0"),n("6c02")),m={class:"container mt-5"};function v(t,e,n,o,a,s){var u=Object(r["C"])("BotStatus");return Object(r["w"])(),Object(r["g"])("div",m,[Object(r["k"])(u)])}var g=Object(r["j"])(" The bot is currently "),j=Object(r["h"])("h2",null,"Actions",-1),O=Object(r["j"])(" Start or restart the bot "),w=Object(r["h"])("br",null,null,-1),S=Object(r["j"])(" Stop the bot "),y=Object(r["h"])("br",null,null,-1);function B(t,e,n,o,a,s){return Object(r["w"])(),Object(r["g"])("div",null,[Object(r["h"])("button",{class:"btn btn-primary",onClick:e[0]||(e[0]=function(t){return s.getBotStatus(!0)})},"get bot status"),Object(r["h"])("p",null,[g,Object(r["h"])("span",{class:Object(r["r"])(["badge",["online"===s.botStatus?"bg-success":"bg-danger"]])},Object(r["F"])("online"===s.botStatus?"Online":"Offline"),3)]),j,Object(r["h"])("p",null,[O,w,Object(r["h"])("button",{class:"btn btn-primary",onClick:e[1]||(e[1]=function(t){return s.startBot()})},Object(r["F"])("online"===s.botStatus?"Restart Bot":"Start Bot"),1)]),Object(r["h"])("p",null,[S,y,Object(r["h"])("button",{class:"btn btn-danger",onClick:e[2]||(e[2]=function(t){return s.stopBot()})},"Stop Bot")])])}var x={name:"BotStatus",props:{},data:function(){return{onlineQueryInterval:null}},computed:{botStatus:function(){return this.$store.getters.getBotStatus}},mounted:function(){var t=this;this.getBotStatus(!1),this.onlineQueryInterval=setInterval((function(){t.getBotStatus(!1)}),5e3)},methods:{getBotStatus:function(t){var e=this;return Object(i["a"])(regeneratorRuntime.mark((function n(){return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:fetch("/api/startUp/botOnline").then(function(){var n=Object(i["a"])(regeneratorRuntime.mark((function n(r){var o;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.next=2,r.json();case 2:o=n.sent,console.log(o),o?(e.$store.commit("setBotStatus","online"),t&&e.toast.success("Bot is online",{timeout:3e3})):(e.$store.commit("setBotStatus","offline"),t&&e.toast.error("Bot is offline",{timeout:3e3}));case 5:case"end":return n.stop()}}),n)})));return function(t){return n.apply(this,arguments)}}());case 1:case"end":return n.stop()}}),n)})))()},startBot:function(){return Object(i["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,fetch("/api/startUp/startBot");case 2:case"end":return t.stop()}}),t)})))()},stopBot:function(){return Object(i["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,fetch("/api/startUp/stopBot");case 2:case"end":return t.stop()}}),t)})))()}}};const k=p()(x,[["render",B]]);var T=k,R={name:"Home",components:{BotStatus:T}};const P=p()(R,[["render",v]]);var L=P,C=[{path:"/",name:"Home",component:L},{path:"/about",name:"About",component:function(){return n.e("about").then(n.bind(null,"f820"))}}],E=Object(h["a"])({history:Object(h["b"])("/"),routes:C}),M=E,_=n("5502"),A=Object(_["a"])({state:{botStatus:"offline"},getters:{getBotStatus:function(t){return t.botStatus}},actions:{},mutations:{setBotStatus:function(t,e){t.botStatus=e}},methods:{}}),$=n("0180"),G=(n("da96"),n("f9e3"),{}),H=Object(r["d"])(d);H.use(A),H.use(M),H.use($["a"],G),H.mixin({data:function(){return{toast:null}},mounted:function(){this.toast=Object($["b"])()},methods:{}}),H.mount("#app")},"840e":function(t,e,n){"use strict";n("10bf")}});
//# sourceMappingURL=app.5f9461d9.js.map