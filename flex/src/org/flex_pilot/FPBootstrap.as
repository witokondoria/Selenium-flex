/*
Copyright 2009, Matthew Eernisse (mde@fleegix.org) and Slide, Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/

package org.flex_pilot {
  import flash.display.Loader;
  import flash.net.URLRequest;
  import flash.events.Event;
  import flash.system.ApplicationDomain;
  import flash.system.SecurityDomain;
  import flash.system.LoaderContext;
  import mx.controls.Alert;
  import flash.events.IOErrorEvent;

  public class FPBootstrap {    
    public static var flex_pilotLibPath:String  = 'FlexPilot-3-stable.swf';    
    public static var wm:*;
    
    public static function onLoadError(e:IOErrorEvent):void {
       // Alert.show ("Failed loading FlexPilot");
    }
    
    public static function init(context:*, domains:* = null):void {
      var loader:Loader = new Loader();
      var url:String = FPBootstrap.flex_pilotLibPath;
      var req:URLRequest = new URLRequest(url);
      
      try {
      	  loader.contentLoaderInfo.addEventListener(
      	      IOErrorEvent.IO_ERROR, onLoadError);
      	      
	      var con:LoaderContext = new LoaderContext(false,
	          ApplicationDomain.currentDomain,
	          SecurityDomain.currentDomain);
	      loader.contentLoaderInfo.addEventListener(
	          Event.COMPLETE, function ():void {
	        wm = ApplicationDomain.currentDomain.getDefinition(
	            "org.flex_pilot.FlexPilot") as Class;
	        wm.init({ context : context, domains: domains });
	      });
	      loader.load(req, con);
	   }
	   catch (error:Error) {
	     Alert.show ("Failed loading FlexPilot");
	   }
    }
  }
}

