$wnd.showcase.runAsyncCallback24("function n5b(a){this.b=a}\nfunction q5b(a){this.b=a}\nfunction t5b(a){this.b=a}\nfunction A5b(a,b){this.b=a;this.c=b}\nfunction _s(a,b){a.remove(b)}\nfunction aFc(a,b){UEc(a,b);_s((arc(),a.hb),b)}\nfunction Tqc(){var a;if(!Qqc||Vqc()){a=new j3c;Uqc(a);Qqc=a}return Qqc}\nfunction Vqc(){var a=$doc.cookie;if(a!=Rqc){Rqc=a;return true}else{return false}}\nfunction Wqc(a){a=encodeURIComponent(a);$doc.cookie=a+'=;expires=Fri, 02-Jan-1970 00:00:00 GMT'}\nfunction i5b(a,b){var c,d,e,f;$s(XEc(a.d));f=0;e=gP(Tqc());for(d=m0c(e);d.b.Fe();){c=enb(s0c(d),1);ZEc(a.d,c);PXc(c,b)&&(f=XEc(a.d).options.length-1)}rp((kp(),jp),new A5b(a,f))}\nfunction j5b(a){var b,c,d,e;if(XEc(a.d).options.length<1){GHc(a.b,f8c);GHc(a.c,f8c);return}d=XEc(a.d).selectedIndex;b=YEc(a.d,d);c=(e=Tqc(),enb(e.ue(b),1));GHc(a.b,b);GHc(a.c,c)}\nfunction Uqc(b){var c=$doc.cookie;if(c&&c!=f8c){var d=c.split(x9c);for(var e=0;e<d.length;++e){var f,g;var i=d[e].indexOf(K9c);if(i==-1){f=d[e];g=f8c}else{f=d[e].substring(0,i);g=d[e].substring(i+1)}if(Sqc){try{f=decodeURIComponent(f)}catch(a){}try{g=decodeURIComponent(g)}catch(a){}}b.we(f,g)}}}\nfunction h5b(a){var b,c,d;c=new GCc(3,3);a.d=new cFc;b=new avc('Delete');zj((arc(),b.hb),Hfd,true);WBc(c,0,0,'<b><b>Existing Cookies:<\\/b><\\/b>');ZBc(c,0,1,a.d);ZBc(c,0,2,b);a.b=new QHc;WBc(c,1,0,'<b><b>Name:<\\/b><\\/b>');ZBc(c,1,1,a.b);a.c=new QHc;d=new avc('Set Cookie');zj(d.hb,Hfd,true);WBc(c,2,0,'<b><b>Value:<\\/b><\\/b>');ZBc(c,2,1,a.c);ZBc(c,2,2,d);Gj(d,new n5b(a),(_y(),_y(),$y));Gj(a.d,new q5b(a),(Ry(),Ry(),Qy));Gj(b,new t5b(a),$y);i5b(a,null);return c}\nJKb(792,1,w6c,n5b);_.Nc=function o5b(a){var b,c,d;c=ps($i(this.b.b),Med);d=ps($i(this.b.c),Med);b=new wmb(fKb(jKb((new umb).q.getTime()),F6c));if(c.length<1){Xrc('You must specify a cookie name');return}Xqc(c,d,b);i5b(this.b,c)};JKb(793,1,x6c,q5b);_.Mc=function r5b(a){j5b(this.b)};JKb(794,1,w6c,t5b);_.Nc=function u5b(a){var b,c;c=XEc(this.b.d).selectedIndex;if(c>-1&&c<XEc(this.b.d).options.length){b=YEc(this.b.d,c);Wqc(b);aFc(this.b.d,c);j5b(this.b)}};JKb(795,1,z6c);_.xc=function y5b(){nNb(this.c,h5b(this.b))};JKb(796,1,{},A5b);_.zc=function B5b(){this.c<XEc(this.b.d).options.length&&bFc(this.b.d,this.c);j5b(this.b)};_.c=0;var Qqc=null,Rqc,Sqc=true;var czb=LWc(Pdd,'CwCookies$1',792),dzb=LWc(Pdd,'CwCookies$2',793),ezb=LWc(Pdd,'CwCookies$3',794),gzb=LWc(Pdd,'CwCookies$5',796);m7c(lo)(24);\n//# sourceURL=showcase-24.js\n")
