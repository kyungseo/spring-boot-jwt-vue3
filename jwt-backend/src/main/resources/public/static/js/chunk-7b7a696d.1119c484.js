(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7b7a696d"],{ed6d:function(e,t,c){e.exports=c.p+"static/img/home-profile.150d6ea3.png"},fde0:function(e,t,c){"use strict";c.r(t);var r=c("7a23"),s=c("ed6d"),n=c.n(s);const u={class:"container d-flex justify-content-center"},a={class:"col-sm-8"},l={class:"card"},o=Object(r["g"])("img",{src:n.a,class:"card-img-top"},null,-1),b={class:"card-body"},i={class:"card-title"},d={class:"card-text"},j=Object(r["g"])("strong",null,"Token:",-1),g=Object(r["g"])("br",null,null,-1),O=Object(r["g"])("strong",null,"Email:",-1),p=Object(r["g"])("br",null,null,-1),h=Object(r["g"])("strong",null,"Authorities:",-1),f=Object(r["g"])("a",{href:"#!",class:"btn btn-primary"},"Button",-1);function m(e,t,c,s,n,m){return Object(r["z"])(),Object(r["f"])("div",u,[Object(r["g"])("div",a,[Object(r["g"])("div",l,[o,Object(r["g"])("div",b,[Object(r["g"])("h5",i,Object(r["K"])(m.currentUser.userRealName)+" Profile",1),Object(r["g"])("p",d,[j,Object(r["i"])(" "+Object(r["K"])(m.currentUser.accessToken.substring(0,20))+" ... "+Object(r["K"])(m.currentUser.accessToken.substr(m.currentUser.accessToken.length-20))+" ",1),g,O,Object(r["i"])(" "+Object(r["K"])(m.currentUser.userEmail)+" ",1),p,h,Object(r["i"])(" "+Object(r["K"])(m.currentUser.roles),1)]),f])])])])}c("14d9");var U={name:"UserProfile",computed:{currentUser(){return this.$store.state.auth.user}},mounted(){this.currentUser||this.$router.push("/auth/login")}},k=c("6b0d"),v=c.n(k);const w=v()(U,[["render",m]]);t["default"]=w}}]);
//# sourceMappingURL=chunk-7b7a696d.1119c484.js.map