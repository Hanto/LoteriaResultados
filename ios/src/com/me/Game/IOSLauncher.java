package com.me.Game;

import interfaces.ActivityLauncher;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.bindings.admob.GADAdSizeManager;
import org.robovm.bindings.admob.GADBannerView;
import org.robovm.bindings.admob.GADBannerViewDelegateAdapter;
import org.robovm.bindings.admob.GADInterstitial;
import org.robovm.bindings.admob.GADInterstitialDelegateAdapter;
import org.robovm.bindings.admob.GADRequest;
import org.robovm.bindings.admob.GADRequestError;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication.Delegate;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.badlogic.gdx.utils.Logger;

public class IOSLauncher extends Delegate implements ActivityLauncher
{
	private static final Logger log = new Logger(IOSLauncher.class.getName(), Application.LOG_DEBUG);
	private static final boolean USE_TEST_DEVICES = true;
	private GADBannerView adview;
	private GADInterstitial iview;
	private IOSApplication iosApplication;
	
    @Override protected IOSApplication createApplication() 
    {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = false;
        iosApplication = new IOSApplication(new Loto(this), config);
        return iosApplication;
    }

    public static void main(String[] argv) 
    {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
    
    public void inicializarInterstitial()
    {
    	iview = new GADInterstitial();
    	iview.setAdUnitID("ca-app-pub-0229403835501528/2579856197");

    	final UIViewController rootViewController = UIApplication.getSharedApplication().getKeyWindow().getRootViewController();
    	final UIViewController adsViewController = new UIViewController();
    	
    	final GADRequest request = GADRequest.request();
    	iview.loadRequest(request);
    	
    	iview.setDelegate(new GADInterstitialDelegateAdapter()
    	{
    		@Override public void didReceiveAd(GADInterstitial ad) 
            {	UIApplication.getSharedApplication().getKeyWindow().setRootViewController(adsViewController);
    			iview.present(adsViewController); }
    		
    		@Override public void didDismissScreen (GADInterstitial ad)
    		{	UIApplication.getSharedApplication().getKeyWindow().setRootViewController(rootViewController); }

            @Override public void didFailToReceiveAd(GADInterstitial ad, GADRequestError error) 
            {	super.didFailToReceiveAd(ad, error); }
    	});
    	
    }
    
    public void initializarBanners()
    {
    	adview = new GADBannerView(GADAdSizeManager.smartBannerPortrait());
    	adview.setAdUnitID("ca-app-pub-0229403835501528/4453542197");
    	adview.setRootViewController(iosApplication.getUIViewController());
    	iosApplication.getUIViewController().getView().addSubview(adview);
    	
    	final GADRequest request = GADRequest.request();
    	if (USE_TEST_DEVICES) 
    	{
            final NSArray<?> testDevices = new NSArray<NSObject>(new NSString(GADRequest.GAD_SIMULATOR_ID));
            request.setTestDevices(testDevices);
        }

        adview.setDelegate(new GADBannerViewDelegateAdapter() 
        {
            @Override public void didReceiveAd(GADBannerView view) 
            {	super.didReceiveAd(view); }

            @Override public void didFailToReceiveAd(GADBannerView view, GADRequestError error) 
            {	super.didFailToReceiveAd(view, error); }
        });

        adview.loadRequest(request);
        
        final CGSize screenSize = UIScreen.getMainScreen().getBounds().size();
        double screenWidth = screenSize.width();
        double screenHeight = screenSize.height();

        final CGSize adSize = adview.getBounds().size();
        double adWidth = adSize.width();
        double adHeight = adSize.height();

        log.debug(String.format("Mostrando publicidad de tamanyo [%s, %S]", adWidth, adHeight));

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);
       
        adview.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, screenHeight - bannerHeight, bannerWidth, bannerHeight));
    }
    
    @Override public void mostrarPublicidad(boolean mostrar)
    {
    	if (mostrar) 
    	{
    		initializarBanners();
    		inicializarInterstitial();
    	}
    	else
    	{
    		iview = null;
    		adview = null;
    	}
    }

	@Override public void abrirLectorCodigoBarras() 
	{ }

	@Override public void abrirNavegador() 
	{ }
}