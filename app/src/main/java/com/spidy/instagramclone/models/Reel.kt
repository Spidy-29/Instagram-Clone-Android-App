package com.spidy.instagramclone.models

class Reel {

    var reelUrl: String = ""
    var caption: String = ""

    constructor()
    constructor(postUrl: String, caption: String) {
        this.reelUrl = postUrl
        this.caption = caption
    }

}