(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0d03d6"],{"66aa":function(e,t,r){"use strict";r.r(t);var c=r("7a23");const n={class:"container"},s={class:"jumbotron"},u=Object(c["g"])("strong",null,"Token:",-1),b=Object(c["g"])("strong",null,"Id:",-1),l=Object(c["g"])("strong",null,"Email:",-1),j=Object(c["g"])("strong",null,"Authorities:",-1);function o(e,t,r,o,O,i){return Object(c["z"])(),Object(c["f"])("div",n,[Object(c["g"])("header",s,[Object(c["g"])("h3",null,[Object(c["g"])("strong",null,Object(c["K"])(i.currentUser.username),1),Object(c["i"])(" Profile ")])]),Object(c["g"])("p",null,[u,Object(c["i"])(" "+Object(c["K"])(i.currentUser.accessToken.substring(0,20))+" ... "+Object(c["K"])(i.currentUser.accessToken.substr(i.currentUser.accessToken.length-20)),1)]),Object(c["g"])("p",null,[b,Object(c["i"])(" "+Object(c["K"])(i.currentUser.id),1)]),Object(c["g"])("p",null,[l,Object(c["i"])(" "+Object(c["K"])(i.currentUser.email),1)]),j,Object(c["g"])("ul",null,[(Object(c["z"])(!0),Object(c["f"])(c["a"],null,Object(c["F"])(i.currentUser.roles,e=>(Object(c["z"])(),Object(c["f"])("li",{key:e},Object(c["K"])(e),1))),128))])])}r("14d9");var O={name:"Profile",computed:{currentUser(){return this.$store.state.auth.user}},mounted(){this.currentUser||this.$router.push("/login")}},i=r("6b0d"),a=r.n(i);const g=a()(O,[["render",o]]);t["default"]=g}}]);
//# sourceMappingURL=chunk-2d0d03d6.b4f888d3.js.map