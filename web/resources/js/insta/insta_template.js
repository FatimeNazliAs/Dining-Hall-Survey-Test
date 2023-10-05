var userFeed = new Instafeed({
    get: 'user',
    userId: 'digiturkcoukcomtr',
    clientId: '528632765352555',
    accessToken: 'IGQVJVOFZAXa3Mtdk1jYWNib2pHaWxmS1lOSm5sOFBCU0FoODlrT01pdDRLVkFXOW9TUDl1cm1VTTVQUjU0WUhzbW9LSW5qbmg5bWtPLS1QY29YeTV2WGRrNWJZAYnREcWEydmRXSVFPYVdES2UxdnlVbwZDZD',
    resolution: 'standard_resolution',
    template: '<a href="{{link}}" target="_blank" id="{{id}}"><img src="{{image}}" /></a>',
    sortBy: 'most-recent',
    limit: 9,
    links: false
});

userFeed.run();