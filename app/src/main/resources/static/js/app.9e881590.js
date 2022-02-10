(function(t){function e(e){for(var n,o,c=e[0],s=e[1],l=e[2],u=0,b=[];u<c.length;u++)o=c[u],Object.prototype.hasOwnProperty.call(r,o)&&r[o]&&b.push(r[o][0]),r[o]=0;for(n in s)Object.prototype.hasOwnProperty.call(s,n)&&(t[n]=s[n]);d&&d(e);while(b.length)b.shift()();return i.push.apply(i,l||[]),a()}function a(){for(var t,e=0;e<i.length;e++){for(var a=i[e],n=!0,o=1;o<a.length;o++){var s=a[o];0!==r[s]&&(n=!1)}n&&(i.splice(e--,1),t=c(c.s=a[0]))}return t}var n={},r={app:0},i=[];function o(t){return c.p+"js/"+({about:"about"}[t]||t)+"."+{about:"970298f1"}[t]+".js"}function c(e){if(n[e])return n[e].exports;var a=n[e]={i:e,l:!1,exports:{}};return t[e].call(a.exports,a,a.exports,c),a.l=!0,a.exports}c.e=function(t){var e=[],a=r[t];if(0!==a)if(a)e.push(a[2]);else{var n=new Promise((function(e,n){a=r[t]=[e,n]}));e.push(a[2]=n);var i,s=document.createElement("script");s.charset="utf-8",s.timeout=120,c.nc&&s.setAttribute("nonce",c.nc),s.src=o(t);var l=new Error;i=function(e){s.onerror=s.onload=null,clearTimeout(u);var a=r[t];if(0!==a){if(a){var n=e&&("load"===e.type?"missing":e.type),i=e&&e.target&&e.target.src;l.message="Loading chunk "+t+" failed.\n("+n+": "+i+")",l.name="ChunkLoadError",l.type=n,l.request=i,a[1](l)}r[t]=void 0}};var u=setTimeout((function(){i({type:"timeout",target:s})}),12e4);s.onerror=s.onload=i,document.head.appendChild(s)}return Promise.all(e)},c.m=t,c.c=n,c.d=function(t,e,a){c.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:a})},c.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},c.t=function(t,e){if(1&e&&(t=c(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var a=Object.create(null);if(c.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var n in t)c.d(a,n,function(e){return t[e]}.bind(null,n));return a},c.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return c.d(e,"a",e),e},c.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},c.p="/",c.oe=function(t){throw console.error(t),t};var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=e,s=s.slice();for(var u=0;u<s.length;u++)e(s[u]);var d=l;i.push([0,"chunk-vendors"]),a()})({0:function(t,e,a){t.exports=a("cd49")},"4f5b":function(t,e,a){},cbb4:function(t,e,a){"use strict";a("4f5b")},cd49:function(t,e,a){"use strict";a.r(e);a("e260"),a("e6cf"),a("cca6"),a("a79d");var n=a("7a23"),r={id:"app"},i={id:"main"},o=Object(n["i"])('<div class="p-5 bg-primary text-white text-center"><h1>LemonTTB</h1><p>LemonTTB is a Discord Bot designed to assist the GM during tabletop sessions, that use Discord for communication.</p></div><nav class="navbar navbar-expand-sm navbar-dark bg-dark"><div class="container-fluid"><ul class="navbar-nav"><li class="nav-item"><a class="nav-link" href="https://github.com/mProjectsCode/LemonTTB">GitHub</a></li><li class="nav-item"><a class="nav-link" href="#">About LemonTTB</a></li></ul></div></nav>',2),c=Object(n["h"])("footer",{class:"mt-5 p-4 text-center bg-dark",style:{"z-index":"11"}},[Object(n["h"])("p",null,"LemonTTB - GPL-3.0 License")],-1);function s(t,e,a,s,l,u){var d=Object(n["D"])("router-view");return Object(n["w"])(),Object(n["g"])("div",r,[Object(n["h"])("div",i,[o,Object(n["k"])(d),c])])}var l=a("1da1"),u=a("bee2"),d=a("d4ec"),b=a("262e"),p=a("2caf"),O=(a("96cf"),a("d3b7"),a("159b"),a("b0c0"),a("9ab4")),h=a("ce1f"),v=function(t){Object(b["a"])(a,t);var e=Object(p["a"])(a);function a(){return Object(d["a"])(this,a),e.apply(this,arguments)}return Object(u["a"])(a)}(h["b"]);v=Object(O["a"])([Object(h["a"])({data:function(){return{source:null}},mounted:function(){this.subscribeToEvents()},computed:{botStatus:function(){return this.$store.getters.getBotStatus}},methods:{getAllEvents:function(){var t=this;return Object(l["a"])(regeneratorRuntime.mark((function e(){var a,n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,fetch("api/events/getAll");case 2:return a=e.sent,e.next=5,a.json();case 5:n=e.sent,n.forEach((function(e){t.emitter.emit("api-event",e)}));case 7:case"end":return e.stop()}}),e)})))()},subscribeToEvents:function(){var t=this;return Object(l["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,fetch("api/events/unsubscribe");case 2:t.source=new EventSource("/api/events/subscribe"),t.source.onmessage=t.onEventMessage;case 4:case"end":return e.stop()}}),e)})))()},onEventMessage:function(t){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:n=JSON.parse(t.data),console.log(n),e.emitter.emit("api-event",n),e.toast.success(n.name+" status "+n.payload.response,{timeout:5e3});case 4:case"end":return a.stop()}}),a)})))()}}})],v);var g=v,j=(a("cbb4"),a("6b0d")),f=a.n(j);const m=f()(g,[["render",s]]);var y=m,S=(a("3ca3"),a("ddb0"),a("6c02")),P={class:"container mt-5"},w={key:0},x=Object(n["h"])("br",null,null,-1),k=Object(n["h"])("br",null,null,-1);function C(t,e,a,r,i,o){var c=Object(n["D"])("BotStatus"),s=Object(n["D"])("AudioPlayerStatus"),l=Object(n["D"])("Moderation");return Object(n["w"])(),Object(n["g"])("div",P,[Object(n["k"])(c),"online"===t.botStatus?(Object(n["w"])(),Object(n["g"])("div",w,[x,Object(n["k"])(s),k,Object(n["k"])(l)])):Object(n["f"])("",!0)])}var B={class:"card shadow"},A={class:"card-header"},R={style:{display:"flex","align-items":"center",gap:"10px"}},T=Object(n["h"])("span",null," Bot Status ",-1),U={style:{flex:"1"}},E=Object(n["h"])("span",{class:"material-icons",style:{"vertical-align":"middle"}},"refresh",-1),M=[E],G=Object(n["h"])("span",{class:"material-icons",style:{"vertical-align":"middle"}},"autorenew",-1),I=[G],N=Object(n["h"])("h6",{class:""}," Information if the bot is online and buttons to start and stop it. ",-1),$={class:"card-body"},_=Object(n["h"])("h4",null,"Actions",-1),Q=Object(n["h"])("h4",null,"Component Status",-1),D={class:"container"},L={class:"row"},V={class:"col-auto"},F=Object(n["h"])("div",{class:"col"}," Audio Player ",-1),J={class:"row"},H={class:"col-auto"},K=Object(n["h"])("div",{class:"col"}," User Manager ",-1),q={class:"row"},Y={class:"col-auto"},z=Object(n["h"])("div",{class:"col"}," Permission Manager ",-1),W={class:"row"},X={class:"col-auto"},Z=Object(n["h"])("div",{class:"col"}," Name Mappings ",-1),tt={class:"row"},et={class:"col-auto"},at=Object(n["h"])("div",{class:"col"}," JDA ",-1),nt={class:"row"},rt={class:"col-auto"},it=Object(n["h"])("div",{class:"col"}," Config Validated ",-1),ot=Object(n["h"])("br",null,null,-1),ct=Object(n["h"])("h6",null,"Bot start up errors:",-1),st={class:"text-danger"};function lt(t,e,a,r,i,o){return Object(n["w"])(),Object(n["g"])("div",B,[Object(n["h"])("div",A,[Object(n["h"])("h2",R,[T,Object(n["h"])("span",U,[Object(n["h"])("span",{class:Object(n["r"])(["badge",[t.botStatusBadgeClass]])},Object(n["G"])(t.botStatusBadgeContent),3)]),Object(n["h"])("button",{class:"btn btn-secondary",type:"button",onClick:e[0]||(e[0]=function(e){return t.getBotStatus(!0)})},M),Object(n["h"])("button",{class:Object(n["r"])(["btn",[t.data.autoOnlineQuery?"btn-success":"btn-secondary"]]),type:"button",onClick:e[1]||(e[1]=function(e){return t.switchAutoOnlineQueryMode()})},I,2)]),N]),Object(n["h"])("div",$,[_,Object(n["h"])("p",null,[Object(n["h"])("button",{class:Object(n["r"])(["btn",["online"===t.botStatus?"btn-secondary":"btn-primary"]]),onClick:e[2]||(e[2]=function(e){return t.startBot()})},Object(n["G"])("online"===t.botStatus?"Restart Bot":"Start Bot"),3)]),Object(n["h"])("p",null,[Object(n["h"])("button",{class:"btn btn-danger",onClick:e[3]||(e[3]=function(e){return t.stopBot()})},"Stop Bot")]),Q,Object(n["h"])("div",D,[Object(n["h"])("div",L,[Object(n["h"])("div",V,[Object(n["h"])("span",{class:Object(n["r"])(["material-icons",[t.data.botAudioPlayerOnline?"text-success":"text-danger"]]),style:{"vertical-align":"middle"}},Object(n["G"])(t.data.botAudioPlayerOnline?"check":"clear"),3)]),F]),Object(n["h"])("div",J,[Object(n["h"])("div",H,[Object(n["h"])("span",{class:Object(n["r"])(["material-icons",[t.data.botUserOnline?"text-success":"text-danger"]]),style:{"vertical-align":"middle"}},Object(n["G"])(t.data.botUserOnline?"check":"clear"),3)]),K]),Object(n["h"])("div",q,[Object(n["h"])("div",Y,[Object(n["h"])("span",{class:Object(n["r"])(["material-icons",[t.data.botPermissionsOnline?"text-success":"text-danger"]]),style:{"vertical-align":"middle"}},Object(n["G"])(t.data.botPermissionsOnline?"check":"clear"),3)]),z]),Object(n["h"])("div",W,[Object(n["h"])("div",X,[Object(n["h"])("span",{class:Object(n["r"])(["material-icons",[t.data.botNameMappingsOnline?"text-success":"text-danger"]]),style:{"vertical-align":"middle"}},Object(n["G"])(t.data.botNameMappingsOnline?"check":"clear"),3)]),Z]),Object(n["h"])("div",tt,[Object(n["h"])("div",et,[Object(n["h"])("span",{class:Object(n["r"])(["material-icons",[t.data.botJdaOnline?"text-success":"text-danger"]]),style:{"vertical-align":"middle"}},Object(n["G"])(t.data.botJdaOnline?"check":"clear"),3)]),at]),Object(n["h"])("div",nt,[Object(n["h"])("div",rt,[Object(n["h"])("span",{class:Object(n["r"])(["material-icons",[t.data.botConfigValidationOnline?"text-success":"text-danger"]]),style:{"vertical-align":"middle"}},Object(n["G"])(t.data.botConfigValidationOnline?"check":"clear"),3)]),it])]),Object(n["N"])(Object(n["h"])("div",null,[ot,ct,Object(n["h"])("p",st,Object(n["G"])(t.data.botStartUpError),1)],512),[[n["K"],void 0!=t.data.botStartUpError]])])])}a("fb6a");var ut=function(t){Object(b["a"])(a,t);var e=Object(p["a"])(a);function a(){return Object(d["a"])(this,a),e.apply(this,arguments)}return Object(u["a"])(a)}(h["b"]);ut=Object(O["a"])([Object(h["a"])({name:"BotStatus",props:{},data:function(){var t={onlineQueryInterval:void 0,autoOnlineQuery:!1,botStartUpError:void 0,botAudioPlayerOnline:!1,botUserOnline:!1,botPermissionsOnline:!1,botNameMappingsOnline:!1,botJdaOnline:!1,botConfigValidationOnline:!1};return{data:t}},computed:{botStatus:function(){return this.$store.getters.getBotStatus},botStatusBadgeClass:function(){var t=this.$store.getters.getBotStatus;switch(t){case"online":return"badge-success";case"starting":return"badge-secondary";default:return"badge-danger"}},botStatusBadgeContent:function(){var t=this.$store.getters.getBotStatus;return this.data.botStartUpError?"Error":t.charAt(0).toUpperCase()+t.slice(1)}},mounted:function(){var t=this;this.getBotStatus(!1),this.emitter.on("api-event",(function(e){if("BOT_START_UP"==e.eventType)switch(console.log("received api event in BotStatus.vue"),e.name){case"AUDIO_PLAYER":t.data.botAudioPlayerOnline=!0;break;case"USERS":t.data.botUserOnline=!0;break;case"PERMISSIONS":t.data.botPermissionsOnline=!0;break;case"NAME_MAPPINGS":t.data.botNameMappingsOnline=!0;break;case"JDA":t.data.botJdaOnline=!0;break;case"CONFIG_VALIDATION":t.data.botConfigValidationOnline=!0;break}else if("BOT_STATUS"==e.eventType){if(console.log("received api event in BotStatus.vue"),"STOP"==e.name)t.getBotStatus(!1);else if("STARTING"==e.name)t.$store.commit("setBotStatus","starting"),t.data.botStartUpError=null,t.data.botAudioPlayerOnline=!1,t.data.botUserOnline=!1,t.data.botPermissionsOnline=!1,t.data.botNameMappingsOnline=!1,t.data.botJdaOnline=!1,t.data.botConfigValidationOnline=!1;else if("ONLINE"==e.name){t.$store.commit("setBotStatus","online");var a=t.$store.getters.getFetchOnStartUp;a.forEach((function(t){fetch(t)})),t.$store.commit("clearFetchOnStartUp")}}else"BOT_ERROR"==e.eventType&&(console.log("received api event in BotStatus.vue"),t.data.botStartUpError=e.payload.data)}))},methods:{getBotStatus:function(t){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function a(){return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:fetch("/api/startUp/botOnline").then(function(){var a=Object(l["a"])(regeneratorRuntime.mark((function a(n){var r;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.next=2,n.json();case 2:r=a.sent,console.log(r),r?(e.$store.commit("setBotStatus","online"),e.data.botAudioPlayerOnline=!0,e.data.botUserOnline=!0,e.data.botPermissionsOnline=!0,e.data.botNameMappingsOnline=!0,e.data.botJdaOnline=!0,e.data.botConfigValidationOnline=!0,t&&e.toast.success("Bot is online",{timeout:3e3})):(e.$store.commit("setBotStatus","offline"),e.$store.commit("setAudioPlayerStatus","disconnected"),e.data.botAudioPlayerOnline=!1,e.data.botUserOnline=!1,e.data.botPermissionsOnline=!1,e.data.botNameMappingsOnline=!1,e.data.botJdaOnline=!1,e.data.botConfigValidationOnline=!1,t&&e.toast.error("Bot is offline",{timeout:3e3}));case 5:case"end":return a.stop()}}),a)})));return function(t){return a.apply(this,arguments)}}());case 1:case"end":return a.stop()}}),a)})))()},startBot:function(){return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,fetch("/api/startUp/startBot");case 2:case"end":return t.stop()}}),t)})))()},stopBot:function(){return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,fetch("/api/startUp/stopBot");case 2:case"end":return t.stop()}}),t)})))()},switchAutoOnlineQueryMode:function(){var t=this;return Object(l["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(t.data.autoOnlineQuery=!t.data.autoOnlineQuery,!t.data.autoOnlineQuery){e.next=6;break}return e.next=4,t.startAutoOnlineQuery();case 4:e.next=8;break;case 6:return e.next=8,t.stopAutoOnlineQuery();case 8:case"end":return e.stop()}}),e)})))()},startAutoOnlineQuery:function(){var t=this;return Object(l["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:t.data.onlineQueryInterval=setInterval((function(){t.getBotStatus(!1)}),5e3);case 1:case"end":return e.stop()}}),e)})))()},stopAutoOnlineQuery:function(){var t=this;return Object(l["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:clearInterval(t.onlineQueryInterval);case 1:case"end":return e.stop()}}),e)})))()}}})],ut);var dt=ut;const bt=f()(dt,[["render",lt]]);var pt=bt,Ot={class:"card shadow"},ht={class:"card-header"},vt={style:{display:"flex","align-items":"center",gap:"10px"}},gt=Object(n["h"])("span",null," Audio Player Status ",-1),jt={style:{flex:"1"}},ft=Object(n["h"])("h6",{class:""}," Information about the audio player of the bot. ",-1),mt={class:"card-body"},yt={key:0},St=Object(n["h"])("b",null,"Connected to ",-1),Pt=Object(n["h"])("b",null," in ",-1),wt=Object(n["h"])("br",null,null,-1),xt=Object(n["h"])("br",null,null,-1),kt={key:1},Ct=Object(n["h"])("b",null,"Playing",-1),Bt={class:"progress bg-dark",style:{"margin-top":"10px","margin-bottom":"15px"}},At=["aria-valuenow"],Rt={style:{display:"flex","align-items":"center",gap:"10px"}},Tt={class:"material-icons",style:{"vertical-align":"middle"}},Ut=Object(n["h"])("span",{class:"material-icons",style:{"vertical-align":"middle"}},"skip_next",-1),Et=[Ut],Mt=Object(n["h"])("span",{class:"material-icons",style:{"vertical-align":"middle"}},"all_inclusive",-1),Gt=[Mt],It=Object(n["h"])("span",{style:{flex:"1"}},null,-1),Nt=Object(n["h"])("br",null,null,-1),$t={style:{display:"flex",gap:"10px"}},_t=Object(n["h"])("div",{style:{flex:"1"}},[Object(n["h"])("b",null,"Queue")],-1),Qt=Object(n["h"])("span",{class:"material-icons",style:{"vertical-align":"middle"}},"clear_all",-1),Dt=[Qt],Lt=Object(n["h"])("div",{style:{"margin-bottom":"8px"}},null,-1),Vt={style:{"margin-top":"2px",display:"flex",gap:"10px"}},Ft={style:{flex:"1"}},Jt={style:{"vertical-align":"middle"}},Ht={style:{"vertical-align":"middle"}},Kt=["onClick"],qt=Object(n["h"])("span",{class:"material-icons",style:{"vertical-align":"middle"}},"clear",-1),Yt=[qt],zt={key:2};function Wt(t,e,a,r,i,o){var c,s,l,u,d,b,p;return Object(n["w"])(),Object(n["g"])("div",Ot,[Object(n["h"])("div",ht,[Object(n["h"])("h2",vt,[gt,Object(n["h"])("span",jt,[Object(n["h"])("span",{class:Object(n["r"])(["badge",[t.audioPlayerStatusBadgeClass]])},Object(n["G"])(t.audioPlayerStatusBadgeContent),3)])]),ft]),Object(n["h"])("div",mt,["disconnected"!==t.audioPlayerStatus?(Object(n["w"])(),Object(n["g"])("div",yt,[St,Object(n["j"])(" "+Object(n["G"])(null===(c=t.data.audioPlayerState)||void 0===c?void 0:c.channelName)+" ",1),Pt,Object(n["j"])(" "+Object(n["G"])(null===(s=t.data.audioPlayerState)||void 0===s?void 0:s.guildName)+" ",1),wt,xt])):Object(n["f"])("",!0),"playing"===t.audioPlayerStatus||"paused"===t.audioPlayerStatus?(Object(n["w"])(),Object(n["g"])("div",kt,[Ct,Object(n["j"])(" "+Object(n["G"])(null===(l=t.data.audioPlayerState)||void 0===l||null===(u=l.currentTrack)||void 0===u?void 0:u.name)+" ",1),Object(n["h"])("div",Bt,[Object(n["h"])("div",{"aria-valuemax":"100","aria-valuemin":"0",class:"progress-bar",role:"progressbar","aria-valuenow":[t.getProgress],style:Object(n["s"])(["width: "+t.getProgress+"%;"])},null,12,At)]),Object(n["h"])("div",Rt,[Object(n["h"])("button",{class:"btn btn-primary",type:"button",onClick:e[0]||(e[0]=function(e){return t.switchPause()})},[Object(n["h"])("span",Tt,Object(n["G"])(null!==(d=t.data.audioPlayerState)&&void 0!==d&&d.paused?"play_arrow":"pause"),1)]),Object(n["h"])("button",{class:"btn btn-secondary",type:"button",onClick:e[1]||(e[1]=function(e){return t.skip()})},Et),Object(n["h"])("button",{class:Object(n["r"])(["btn",[null!==(b=t.data.audioPlayerState)&&void 0!==b&&b.looping?"btn-success":"btn-secondary"]]),type:"button",onClick:e[2]||(e[2]=function(e){return t.switchLooping()})},Gt,2),It,Object(n["h"])("span",null,Object(n["G"])(t.getProgressLabel),1)]),Nt,Object(n["h"])("div",$t,[_t,Object(n["h"])("div",null,[Object(n["h"])("button",{class:"btn btn-danger btn-sm",style:{"vertical-align":"middle",padding:"0px 6px 0px 6px"},type:"button",onClick:e[3]||(e[3]=function(){return t.clearQueue&&t.clearQueue.apply(t,arguments)})},Dt)])]),Lt,(Object(n["w"])(!0),Object(n["g"])(n["a"],null,Object(n["B"])(null===(p=t.data.audioPlayerState)||void 0===p?void 0:p.queue,(function(e,a){return Object(n["w"])(),Object(n["g"])("div",Vt,[Object(n["h"])("div",Ft,[Object(n["h"])("div",Jt,Object(n["G"])(e.name),1)]),Object(n["h"])("div",null,[Object(n["h"])("div",Ht,Object(n["G"])(new Date(1e3*e.length).toISOString().substr(14,5)),1)]),Object(n["h"])("div",null,[Object(n["h"])("button",{class:"btn btn-danger btn-sm",style:{"vertical-align":"middle",padding:"0 6px 0 6px"},type:"button",onClick:function(e){return t.removeFromQueue(a)}},Yt,8,Kt)])])})),256))])):(Object(n["w"])(),Object(n["g"])("div",zt," No track currently playing. "))])])}var Xt=function(t){Object(b["a"])(a,t);var e=Object(p["a"])(a);function a(){return Object(d["a"])(this,a),e.apply(this,arguments)}return Object(u["a"])(a)}(h["b"]);Xt=Object(O["a"])([Object(h["a"])({name:"AudioPlayerStatus",props:{},data:function(){var t={audioPlayerError:void 0,audioPlayerState:void 0,audioPlayerProgressInterval:void 0,audioPlayerProgress:0,audioPlayerPlaying:!1};return{data:t}},computed:{audioPlayerStatus:function(){return this.$store.getters.getAudioPlayerStatus},audioPlayerStatusBadgeClass:function(){var t=this.$store.getters.getAudioPlayerStatus;switch(t){case"playing":return"badge-success";case"paused":return"badge-secondary";case"connected":return"badge-secondary";default:return"badge-danger"}},audioPlayerStatusBadgeContent:function(){var t=this.$store.getters.getAudioPlayerStatus;return this.data.audioPlayerError?"Error":t.charAt(0).toUpperCase()+t.slice(1)},getProgress:function(){var t,e;return 100*this.data.audioPlayerProgress/(null===(t=this.data.audioPlayerState)||void 0===t||null===(e=t.currentTrack)||void 0===e?void 0:e.length)},getProgressLabel:function(){var t,e,a=new Date(1e3*this.data.audioPlayerProgress).toISOString().substr(14,5),n=new Date(1e3*(null===(t=this.data.audioPlayerState)||void 0===t||null===(e=t.currentTrack)||void 0===e?void 0:e.length)).toISOString().substr(14,5);return a+" / "+n}},mounted:function(){var t=this;this.getStatus(),this.emitter.on("api-event",(function(e){if("AUDIO_PLAYER"==e.eventType){console.log("received api event in AudioPlayerStatus.vue"),t.data.audioPlayerState=e.payload.data;var a=t.data.audioPlayerState.channelName&&t.data.audioPlayerState.guildName,n=void 0!=t.data.audioPlayerState.currentTrack,r=!n||t.data.audioPlayerState.currentTrack.position>=t.data.audioPlayerState.currentTrack.length;a?"STARTED_TRACK"!=e.payload.response&&"STATUS_REQUEST"!=e.payload.response||r?"FINISHED_TRACK"==e.payload.response&&(t.data.audioPlayerPlaying=!1):t.data.audioPlayerPlaying=!0:t.data.audioPlayerPlaying=!1,a?t.data.audioPlayerPlaying?t.data.audioPlayerState.paused?t.$store.commit("setAudioPlayerStatus","paused"):t.$store.commit("setAudioPlayerStatus","playing"):t.$store.commit("setAudioPlayerStatus","connected"):t.$store.commit("setAudioPlayerStatus","disconnected"),n&&(console.log("has current track"),t.data.audioPlayerProgress=t.data.audioPlayerState.currentTrack.position,t.data.audioPlayerProgressInterval&&clearInterval(t.data.audioPlayerProgressInterval),!t.data.audioPlayerState.paused&&t.data.audioPlayerPlaying&&(t.data.audioPlayerProgressInterval=setInterval((function(){t.data.audioPlayerProgress+=1}),1e3)))}}))},methods:{getStatus:function(){var t=this;return Object(l["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if("online"!==t.$store.getters.getBotStatus){e.next=5;break}return e.next=3,fetch("api/audioPlayer/getStatus");case 3:e.next=6;break;case 5:t.$store.commit("addToFetchOnStartUp","api/audioPlayer/getStatus");case 6:case"end":return e.stop()}}),e)})))()},switchPause:function(){return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,fetch("api/audioPlayer/switchPause");case 2:case"end":return t.stop()}}),t)})))()},switchLooping:function(){return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,fetch("api/audioPlayer/switchLooping");case 2:case"end":return t.stop()}}),t)})))()},skip:function(){return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,fetch("api/audioPlayer/skip");case 2:case"end":return t.stop()}}),t)})))()},clearQueue:function(){return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,fetch("api/audioPlayer/clearQueue");case 2:case"end":return t.stop()}}),t)})))()},removeFromQueue:function(t){return Object(l["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,fetch("api/audioPlayer/removeFromQueue/"+t);case 2:case"end":return e.stop()}}),e)})))()},joinVC:function(){return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:case"end":return t.stop()}}),t)})))()},leaveVC:function(){return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:case"end":return t.stop()}}),t)})))()}}})],Xt);var Zt=Xt;const te=f()(Zt,[["render",Wt]]);var ee=te,ae={class:"card shadow"},ne=Object(n["i"])('<div class="card-header"><h2 style="display:flex;align-items:center;gap:10px;"><span> Moderation </span><span style="flex:1;"><span class="badge badge-success">Online</span></span></h2><h6 class=""> Moderate the two table top voice channels. </h6></div>',1),re={class:"card-body"},ie={style:{display:"flex",gap:"10px"}},oe={style:{flex:"1"}},ce=Object(n["h"])("b",null,"Primary Voice Channel ",-1),se=Object(n["h"])("div",{style:{"margin-bottom":"8px"}},null,-1),le={style:{"margin-top":"2px",display:"flex",gap:"10px"}},ue={style:{"vertical-align":"middle"}},de={style:{flex:"1"}},be={style:{"vertical-align":"middle"}},pe=["onClick"],Oe=Object(n["h"])("span",{class:"material-icons",style:{"vertical-align":"middle"}},"headset_off",-1),he=[Oe],ve=Object(n["h"])("br",null,null,-1),ge={style:{display:"flex",gap:"10px"}},je={style:{flex:"1"}},fe=Object(n["h"])("b",null,"Secondary Voice Channel ",-1),me=Object(n["h"])("div",{style:{"margin-bottom":"8px"}},null,-1),ye={style:{"margin-top":"2px",display:"flex",gap:"10px"}},Se={style:{"vertical-align":"middle"}},Pe={style:{flex:"1"}},we={style:{"vertical-align":"middle"}},xe=["onClick"],ke=Object(n["h"])("span",{class:"material-icons",style:{"vertical-align":"middle"}},"headset_off",-1),Ce=[ke];function Be(t,e,a,r,i,o){var c,s,l,u;return Object(n["w"])(),Object(n["g"])("div",ae,[ne,Object(n["h"])("div",re,[Object(n["h"])("div",ie,[Object(n["h"])("div",oe,[ce,Object(n["j"])(" "+Object(n["G"])(null===(c=t.data.voiceChannelState)||void 0===c?void 0:c.primaryChannelName),1)])]),se,(Object(n["w"])(!0),Object(n["g"])(n["a"],null,Object(n["B"])(null===(s=t.data.voiceChannelState)||void 0===s?void 0:s.primaryChannelUsers,(function(e,a){return Object(n["w"])(),Object(n["g"])("div",le,[Object(n["h"])("div",null,[Object(n["h"])("div",ue,Object(n["G"])(e.user.id),1)]),Object(n["h"])("div",de,[Object(n["h"])("div",be,Object(n["G"])(e.name),1)]),Object(n["h"])("div",null,[Object(n["h"])("button",{class:Object(n["r"])(["btn btn-sm",[e.deafened?"btn-danger":"btn-secondary"]]),style:{"vertical-align":"middle",padding:"0 6px 0 6px"},type:"button",onClick:function(a){return t.switchDeafMute(e.user.id)}},he,10,pe)])])})),256)),ve,Object(n["h"])("div",ge,[Object(n["h"])("div",je,[fe,Object(n["j"])(" "+Object(n["G"])(null===(l=t.data.voiceChannelState)||void 0===l?void 0:l.secondaryChannelName),1)])]),me,(Object(n["w"])(!0),Object(n["g"])(n["a"],null,Object(n["B"])(null===(u=t.data.voiceChannelState)||void 0===u?void 0:u.secondaryChannelUsers,(function(e,a){return Object(n["w"])(),Object(n["g"])("div",ye,[Object(n["h"])("div",null,[Object(n["h"])("div",Se,Object(n["G"])(e.user.id),1)]),Object(n["h"])("div",Pe,[Object(n["h"])("div",we,Object(n["G"])(e.name),1)]),Object(n["h"])("div",null,[Object(n["h"])("button",{class:Object(n["r"])(["btn btn-sm",[e.deafened?"btn-danger":"btn-secondary"]]),style:{"vertical-align":"middle",padding:"0 6px 0 6px"},type:"button",onClick:function(a){return t.switchDeafMute(e.user.id)}},Ce,10,xe)])])})),256))])])}var Ae=function(t){Object(b["a"])(a,t);var e=Object(p["a"])(a);function a(){return Object(d["a"])(this,a),e.apply(this,arguments)}return Object(u["a"])(a)}(h["b"]);Ae=Object(O["a"])([Object(h["a"])({name:"Moderation",props:{},data:function(){var t={voiceChannelState:void 0};return{data:t}},computed:{audioPlayerStatus:function(){return this.$store.getters.getAudioPlayerStatus}},mounted:function(){var t=this;this.getStatus(),this.emitter.on("api-event",(function(e){"VOICE_CHANNEL"==e.eventType&&(console.log("received api event in Moderation.vue"),t.data.voiceChannelState=e.payload.data)}))},methods:{getStatus:function(){var t=this;return Object(l["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if("online"!==t.$store.getters.getBotStatus){e.next=5;break}return e.next=3,fetch("api/voiceChannel/getStatus");case 3:e.next=6;break;case 5:t.$store.commit("addToFetchOnStartUp","api/voiceChannel/getStatus");case 6:case"end":return e.stop()}}),e)})))()},switchDeafMute:function(t){return Object(l["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,fetch("api/voiceChannel/switchDeafMute/"+t);case 2:case"end":return e.stop()}}),e)})))()}}})],Ae);var Re=Ae;const Te=f()(Re,[["render",Be]]);var Ue=Te,Ee=function(t){Object(b["a"])(a,t);var e=Object(p["a"])(a);function a(){return Object(d["a"])(this,a),e.apply(this,arguments)}return Object(u["a"])(a)}(h["b"]);Ee=Object(O["a"])([Object(h["a"])({components:{Moderation:Ue,AudioPlayerStatus:ee,BotStatus:pt},computed:{botStatus:function(){return this.$store.getters.getBotStatus}}})],Ee);var Me=Ee;const Ge=f()(Me,[["render",C]]);var Ie=Ge,Ne=[{path:"/",name:"Dashboard",component:Ie},{path:"/about",name:"About",component:function(){return a.e("about").then(a.bind(null,"f820"))}}],$e=Object(S["a"])({history:Object(S["b"])("/"),routes:Ne}),_e=$e,Qe=a("5502"),De={botStatus:"offline",audioPlayerStatus:"disconnected",eventSource:void 0,fetchOnStartUp:[]},Le=Object(Qe["a"])({state:{data:De},getters:{getBotStatus:function(t){return t.data.botStatus},getAudioPlayerStatus:function(t){return t.data.audioPlayerStatus},getFetchOnStartUp:function(t){return t.data.fetchOnStartUp}},actions:{},mutations:{setBotStatus:function(t,e){t.data.botStatus=e},setAudioPlayerStatus:function(t,e){t.data.audioPlayerStatus=e},addToFetchOnStartUp:function(t,e){t.data.fetchOnStartUp.push(e)},clearFetchOnStartUp:function(t){t.data.fetchOnStartUp=[]}}}),Ve=a("0180"),Fe=(a("da96"),a("1375"),a("1344")),Je={},He=Object(Fe["a"])(),Ke=Object(n["d"])(y);Ke.use(Le),Ke.use(_e),Ke.use(Ve["a"],Je),Ke.config.globalProperties.emitter=He,Ke.mixin({data:function(){return{toast:null}},mounted:function(){this.toast=Object(Ve["b"])()},methods:{}}),Ke.mount("#app")}});
//# sourceMappingURL=app.9e881590.js.map