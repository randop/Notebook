javascript: (function() {
    (function() {
        var i, elements = document.querySelectorAll('body *');
        for (i = 0; i < elements.length; i++) {
            if (getComputedStyle(elements[i]).position === 'fixed') {
                elements[i].parentNode.removeChild(elements[i]);
            }
            if (getComputedStyle(elements[i]).filter.indexOf("blur")==0) {
                elements[i].style.setProperty('filter', 'none', 'important')
            }
        }
    })();
    document.querySelector('body').style.setProperty('overflow', 'auto', 'important');
    document.querySelector('html').style.setProperty('overflow', 'auto', 'important');
})();
