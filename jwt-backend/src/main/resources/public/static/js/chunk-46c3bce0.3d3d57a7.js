(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-46c3bce0"],{"1a09":function(t,e,s){t.exports=s.p+"static/img/home-staff.724741c0.png"},"76f9":function(t,e,s){"use strict";s.r(e);var c=s("7a23"),a=s("1a09"),n=s.n(a);const o={class:"container d-flex justify-content-center"},r={class:"col-sm-8"},d={class:"card"},i=Object(c["g"])("img",{src:n.a,class:"card-img-top"},null,-1),f={class:"card-body"},b=Object(c["g"])("h5",{class:"card-title"},"Staff Home",-1),p={class:"card-text"},g=Object(c["g"])("a",{href:"#!",class:"btn btn-primary"},"Button",-1);function l(t,e,s,a,n,l){return Object(c["z"])(),Object(c["f"])("div",o,[Object(c["g"])("div",r,[Object(c["g"])("div",d,[i,Object(c["g"])("div",f,[b,Object(c["g"])("p",p,Object(c["K"])(n.content),1),g])])])])}var u=s("1f57"),m=s("30ef"),j={name:"StaffHome",data(){return{content:""}},mounted(){u["a"].getStaffHome().then(t=>{this.content=t.data},t=>{this.content=t.response&&t.response.data&&t.response.data.message||t.message||t.toString(),t.response&&403===t.response.status&&m["a"].dispatch("logout")})}},O=s("6b0d"),h=s.n(O);const v=h()(j,[["render",l]]);e["default"]=v}}]);
//# sourceMappingURL=chunk-46c3bce0.3d3d57a7.js.map