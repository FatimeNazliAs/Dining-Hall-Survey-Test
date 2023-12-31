// Creare's 'Implied Consent' EU Cookie Law Banner v:2.4
// Conceived by Robert Kent, James Bavington & Tom Foyster
// Put into a namespace and by Bjørn Rosell
//   Also changed behaviour so you have to click accept to make the banner stay away.
//   To make it behave like the original, set "createCookieWhenBannerIsShown" to true.

var CookieBanner = (function() {
    return {
        'createCookieWhenBannerIsShown': false,
        'createCookieWhenAcceptIsClicked': true,
        'cookieDuration': 14,                   // Number of days before the cookie expires, and the banner reappears
        'cookieName': 'cookieConsent',          // Name of our cookie
        'cookieValue': 'accepted',              // Value of cookie

        '_createDiv': function(html) {
            var bodytag = document.getElementsByTagName('body')[0];
            var div = document.createElement('div');
            div.setAttribute('id','cookie-law');
            div.innerHTML = html;

            // bodytag.appendChild(div); // Adds the Cookie Law Banner just before the closing </body> tag
            // or
            bodytag.insertBefore(div,bodytag.firstChild); // Adds the Cookie Law Banner just after the opening <body> tag

            document.getElementsByTagName('body')[0].className+=' cookiebanner'; //Adds a class tothe <body> tag when the banner is visible

            if (CookieBanner.createCookieWhenBannerIsShown) {
                CookieBanner.createAcceptCookie();
            }
        },

        '_createCookie': function(name, value, days) {
            var expires;
            if (days) {
                var date = new Date();
                date.setTime(date.getTime()+(days * 24 * 60 * 60 * 1000));
                expires = "; expires=" + date.toGMTString();
            }
            else {
                expires = "";
            }
            document.cookie = name+"="+value+expires+"; path=/";
        },

        '_checkCookie': function(name) {
            var nameEQ = name + "=";
            var ca = document.cookie.split(';');
            for(var i=0;i < ca.length;i++) {
                var c = ca[i];
                while (c.charAt(0)==' ') c = c.substring(1,c.length);
                if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
            }
            return null;
        },

        '_eraseCookie': function(name) {
            CookieBanner._createCookie(name,"",-1);
        },

        'createAcceptCookie': function() {
            CookieBanner._createCookie(CookieBanner.cookieName, CookieBanner.cookieValue, CookieBanner.cookieDuration); // Create the cookie
        },

        'closeBanner': function() {
            var element = document.getElementById('cookie-law');
            element.parentNode.removeChild(element);
        },

        'accept': function() {
            CookieBanner.createAcceptCookie();
            CookieBanner.closeBanner();
        },

        'showUnlessAccepted': function(html) {
            //alert(CookieBanner._checkCookie(CookieBanner.cookieName));
            //alert(document.cookie);
            if(CookieBanner._checkCookie(CookieBanner.cookieName) != CookieBanner.cookieValue)
            {
                CookieBanner._createDiv(html);
            }
        }

    }

})();

window.onload = function(){

    var html = '<div  class="md:w-5/6">' +
        'Sitemizde, deneyiminizi geliştirmek, içeriği ve reklamları kişiselleştirmek ve site trafiği analiz etmek için site içerisinde kullandığınız ' +
        'bilgileri pazarlama ve analiz ortaklarımızla paylaşabiliriz. ' +
        'Daha fazla bilgi için  ' +
        '<a style="color:blue; text-decoration: underline;" href="privacy-policy">Gizlilik Politikamızı</a> inceleyebilirsiniz. ' +
        '"Kabul Et"i tıklayarak, çerezleri ve benzer teknolojileri kullanmamızı kabul etmiş olursunuz.' +
        '</div>'

    // Add the accept button
    html += '<div class="md:w-1/6"><a href="javascript:void(0);" onclick="CookieBanner.accept();"><span >Kabul Et</span></a></div>';

    CookieBanner.showUnlessAccepted(html);
}



