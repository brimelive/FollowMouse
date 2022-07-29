
# FollowMouse

<p align="center">
	<img src="https://github.com/brimelive/FollowMouse/raw/main/assets/logo.svg" alt="FollowMouse Logo" align="center" height="200px" style="border-radius: 2px;">
	<br />
	A program that presses a key when you move your mouse to another monitor.
	<br />
	<sup>
		(useful for automatically changing scenes while livestreaming)
	</sup>
	<br />
	<a href="./LICENSE"><img src="https://img.shields.io/badge/license-MIT-blue.svg"></a>
</p>

<br />
<br />
<br />

<div>
	<img src="https://github.com/brimelive/FollowMouse/raw/main/assets/follow_mouse_demo.gif" alt="FollowMouse Demo" align="right" height="240px" style="border-radius: 2px;">
	<p align="center">
		<br />
		<br />
		<br />
		Follow mouse is a tool for automatically switching scenes in OBS whenever you're live, that way OBS can always be showing the monitor that the mouse is on!
	</p>
</div>

<br />
<br />
<br />
<br />
<br />
<br />

# Usage
1) Grab the latest windows release zip from [here](https://github.com/brimelive/FollowMouse/releases/latest).
2) Double-click the .exe
3) Profit! See the [Configuration Section](#configuration) for info on how to configure what it does.

You can disable the program by right-clicking the tray icon and pressing `Enabled`, the checkmark beside the button tells you whether or not it's enabled currently.

# Configuration
Every monitor is assigned a number (you can see what your monitor number is by looking at the number displayed on the tray icon). For every monitor you can define a keyboard key (and if you want things like shift or alt to be pressed at the same time) that'll get pressed every time you move your mouse to that monitor.

The default config illustrates this by pressing the windows key on monitor #0, and escape on monitor #1. Modify your config with whatever keys you like (e.g `F10` or `DELETE`) and they'll be pressed next time you launch the application.
