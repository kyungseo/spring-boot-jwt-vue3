(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5236df18"],{"03ab":function(t,e,s){t.exports=s.p+"static/img/home-admin.1ff95287.png"},feb8:function(t,e,s){"use strict";s.r(e);var n=s("7a23"),c=s("03ab"),a=s.n(c);const o={class:"container d-flex justify-content-center"},r={class:"col-sm-8"},d={class:"card"},i=Object(n["g"])("img",{src:a.a,class:"card-img-top"},null,-1),b={class:"card-body"},l=Object(n["g"])("h5",{class:"card-title"},"Admin Home",-1),g={class:"card-text"},m=Object(n["g"])("a",{href:"#!",class:"btn btn-primary"},"Button",-1);function p(t,e,s,c,a,p){return Object(n["z"])(),Object(n["f"])("div",o,[Object(n["g"])("div",r,[Object(n["g"])("div",d,[i,Object(n["g"])("div",b,[l,Object(n["g"])("p",g,Object(n["K"])(a.content),1),m])])])])}var u=s("1f57"),f=s("30ef"),j={name:"AdminHome",data(){return{content:""}},methods:{getAllUsers(){const t=Object(n["o"])("toast","");u["a"].getAdminHome().then(t=>{this.content=t.data}).catch(e=>{t.value=e.response&&e.response.data&&e.response.data.message||e.message||e.toString(),e.response&&403===e.response.status&&f["a"].dispatch("logout")})}},mounted(){this.getAllUsers()}},h=s("6b0d"),O=s.n(h);const v=O()(j,[["render",p]]);e["default"]=v}}]);
//# sourceMappingURL=chunk-5236df18.538c014d.js.map