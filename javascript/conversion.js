/**
 * Here is a prototype to convert a number to a readable string respecting the new international standards.
 * https://stackoverflow.com/questions/10420352/converting-file-size-in-bytes-to-human-readable-string/10420404#answer-20463021
 */

Object.defineProperty(Number.prototype,'fileSize',{value:function(a,b,c,d){
    return (a=a?[1e3,'k','B']:[1024,'K','iB'],b=Math,c=b.log,
    d=c(this)/c(a[0])|0,this/b.pow(a[0],d)).toFixed(2)
    +' '+(d?(a[1]+'MGTPEZY')[--d]+a[2]:'Bytes');
   },writable:false,enumerable:false});

console.log((186457865).fileSize()); // default IEC (power 1024)
//177.82 MiB
//KiB,MiB,GiB,TiB,PiB,EiB,ZiB,YiB

console.log((186457865).fileSize(1)); //1,true for SI (power 1000)
//186.46 MB 
//kB,MB,GB,TB,PB,EB,ZB,YB