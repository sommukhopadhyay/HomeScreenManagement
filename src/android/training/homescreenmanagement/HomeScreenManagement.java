package android.training.homescreenmanagement;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class HomeScreenManagement extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener{
/** Called when the activity is first created. */
	String[] apps;
	String appName;
	List<ResolveInfo> res;
	List<Drawable> processicon;
	int index;
	Button AddButton;
	Spinner spin;
	Drawable icon;
	PackageManager p;
	ActivityInfo ai;
	ApplicationInfo ap;


@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	spin = (Spinner)findViewById(R.id.Spinner01);
	AddButton = (Button)findViewById(R.id.Button01);
	AddButton.setOnClickListener(this);
	spin.setOnItemSelectedListener(this);
	fillSpinner(this);

}

private void fillSpinner(Context context){
	p = context.getPackageManager(); 
	
	Intent i = new Intent(Intent.ACTION_MAIN); 
	i.addCategory(Intent.CATEGORY_LAUNCHER); 
	res =p.queryIntentActivities( i,0);
	
	
	
	int NumberOfApplications = res.size();
	apps = new String[NumberOfApplications];
	//String CurrentAppPackage = context.getApplicationContext().getPackageName();
	
	for(int k=0;k<res.size();k++)
	{
	//String AppPackage = res.get(k).activityInfo.applicationInfo.packageName;
	apps[k] = (res.get(k).activityInfo.loadLabel(p)).toString();
	//processicon[k] = p.getApplicationIcon(res.get(k).activityInfo.applicationInfo.packageName);
	
	}
	ArrayAdapter<String> aa=new ArrayAdapter<String>(this,
	android.R.layout.simple_spinner_item,apps);
	aa.setDropDownViewResource(
	android.R.layout.simple_spinner_dropdown_item);
	spin.setAdapter(aa);
}

public void onItemSelected(AdapterView<?> parent,
View v, int position, long id){
appName = apps[position];
index = position;
//Bitmap appicon = 
}

public void onNothingSelected(AdapterView<?> parent){ 
}

public void onClick(View v){

	if (v == AddButton)
	setShortCut(this,appName); 
}

public boolean setShortCut(Context context, String appName) {
	Context cx = null;
	ai = res.get(index).activityInfo; 
	//Drawable iconresource = p.getApplicationIcon(ai.applicationInfo.packageName);
	
	String packageName = ai.packageName;

	try
	{
		cx = createPackageContext(packageName, CONTEXT_IGNORE_SECURITY);
	}
	catch(PackageManager.NameNotFoundException e)
	{
		
	}

//String iconResource = appinfo.dataDir;

	Intent shortcutIntent = new Intent(); 
	shortcutIntent.setClassName(ai.packageName, ai.name); 
	shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	
	Intent intent = new Intent(); 
	intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); 
	
	intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName); 
	
	Resources Res = cx.getResources();
	
	String packagename = ai.packageName;
	
	int idicon = Res.getIdentifier("icon","drawable",packagename);
	
	if(idicon != 0)
	{
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(cx,idicon));
	}
	
	else 
	{
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context,R.drawable.icon));
	}
	
	
	intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT"); 
	context.sendBroadcast(intent); 
	System.out.println("in the shortcutapp on create method completed"); 
	
	return true; 
	}

}