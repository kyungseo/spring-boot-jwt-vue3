(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0a4c1e"],{"0899":function(e,t,n){"use strict";n.r(t);var s=n("7a23");const a={class:"container"},o={class:"jumbotron"};function c(e,t,n,c,r,d){return Object(s["x"])(),Object(s["f"])("div",a,[Object(s["g"])("header",o,[Object(s["g"])("h3",null,Object(s["H"])(r.content),1)])])}var r=n("1f57"),d=n("30ef"),u={name:"User",data(){return{content:""}},mounted(){r["a"].getUserBoard().then(e=>{this.content=e.data},e=>{this.content=e.response&&e.response.data&&e.response.data.message||e.message||e.toString(),e.response&&403===e.response.status&&d["a"].dispatch("logout")})}},i=n("6b0d"),p=n.n(i);const b=p()(u,[["render",c]]);t["default"]=b}}]);
//# sourceMappingURL=chunk-2d0a4c1e.49c77c86.js.map