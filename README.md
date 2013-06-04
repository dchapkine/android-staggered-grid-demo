Android Staggered Grid Demo
============================

After writing [a post about my first android app](http://www.42hacks.com/notes/en/20130511-lessons-learned-from-building-my-first-android-app-part1/ "a post about my first android app") a reader asked me by email to share my implementation of the staggered grid view. So here it is.
Keep in mind that it's NOT a library, just a quick and dirty demo.


So what's the difference between this demo and other existing implementations ?
----------
Good question!, My implementation focus on:

1. Optimizations for BIG staggered grids: under 10000 elements
2. RAM efficiency. It is IMPOSSIBLE to run out of memory with this demo. Even with 10k+ images in the grid view.
3. Lazy loading / unloading of images = Only images visible on screen are in memory
4. Tested and runs very well on real 2.x and 4.x devices:
	4. Android 2.2 | ZTE Blade ( 512 RAM | 600 MHz ARM 11 )
	5. Android 4.1 | GalaxyTab 2 10.1 ( 1G RAM | Dual-core 1 GHz Cortex-A9 )
5. You must know aspect ratio of ALL images in your grid BEFORE you load them in order to compute scroll height. This is NOT a "pull to load" staggered grid view, it just loads while you scroll.
6. It is NOT a library, so please read and understand the code before using it, it is very short and well commented.


Dependency #1
----------
I am using a modified version of [Android Smart Image View](http://loopj.com/android-smart-image-view "Android Smart Image View") to load images. Long story short: android-smart-image-view has an internal cashing system that does NOT fit my usecase. The problem of the curent cashing sustem is that on old 2.x devices with weak RAM, [your app will run out of memory](https://github.com/loopj/android-smart-image-view/issues/12 "your app will run out of memory")... I found no way to disable caching, without updating android-smart-image-view library code... This is why i include it's source in this demo. 

I changed changed 1 method: WebImage::getBitmap ;)



Dependency #2
---
Big thanks to [lorempixel.com for delivering fast and awesome random images](http://lorempixel.com)


Licence
------------------------------------
MIT