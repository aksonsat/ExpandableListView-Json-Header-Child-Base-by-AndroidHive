package info.androidhive.expandablelistview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.expandablelistview.model.ModelCategory;
import info.androidhive.expandablelistview.model.ModelOrder;
import info.androidhive.expandablelistview.parser.JSONParser;

public class MainActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	private ArrayList<ModelCategory> listCategory;

	private ArrayList<ModelOrder> listOrder;

	// url to get category
	private static String url_list_category = "http://supersheek.com/app_sheek/api/get_category.php";

	// url to get order
	private static String url_list_order = "http://supersheek.com/app_sheek/api/get_order_all.php";

	// Tag for category
	private static String TAG_CATEGORY = "category";
	private static String TAG_CATEGORY_NAME = "category_name";
	private static String TAG_CATEGORY_ID = "category_id";

	// Tag fo order
	private static String TAG_ORDER = "order";
	private static String TAG_ORDER_NAME = "order_name";
	private static String TAG_ORDER_PRICE = "order_price";
	private static String TAG_ORDER_CATEOGRY = "order_category";
	private static String TAG_ORDER_IMAGE = "order_image";
	private static String TAG_ORDER_CREATE = "create_at";

	List<String> child;
	String[] strChild;

	List<String> arrChild;
	// ArrayList<String> arrChild;
	List<String> arrChild2;
	List<String> arrChild3;
	List<String> arrChild4;

	List<String> childOrderName;
	List<String> childOrderCategory;

	String[] strArrName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * Array List for Binding Data from JSON to this List
		 */
		listCategory = new ArrayList<>();
		listOrder = new ArrayList<>();
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		child = new ArrayList<>();

		arrChild = new ArrayList<String>();
		arrChild2 = new ArrayList<String>();
		arrChild3 = new ArrayList<String>();
		arrChild4 = new ArrayList<String>();

		childOrderName = new ArrayList<String>();
		childOrderCategory = new ArrayList<String>();

		// AsyncTask
		new AsyncTaskCategory().execute();
		new AsyncTaskOrder().execute();
	}

	/**
	 * Initial to AsyncTask Category
	 */
	class AsyncTaskCategory extends AsyncTask<Void, Void, Void> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			/**
			 * Progress Dialog for User Interaction
			 */
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setTitle("Please with...");
			dialog.setMessage("Downloading...");
			dialog.show();
		}

		@Nullable
		@Override
		protected Void doInBackground(Void... params) {

			Log.i("Check Step", "Step 0");

			/**
			 * Getting JSON Object from Web Using okHttp
			 */
			JSONObject jsonObject = JSONParser.makeHttp(url_list_category);

			try {
				/**
				 * Check Whether Its NULL???
				 */
				if (jsonObject != null) {
					/**
					 * Check Length...
					 */
					if(jsonObject.length() > 0) {
						/**
						 * Getting Array named "contacts" From MAIN Json Object
						 */
						JSONArray array = jsonObject.getJSONArray(TAG_CATEGORY);

						/**
						 * Check Length of Array...
						 */
						int lenArray = array.length();
						if(lenArray > 0) {
							for(int jIndex = 0; jIndex < lenArray; jIndex++) {
								/**
								 * Creating Every time New Object
								 * and
								 * Adding into List
								 */
								ModelCategory model_category = new ModelCategory();

								/**
								 * Getting Inner Object from contacts array...
								 * and
								 * From that We will get Name of that Contact
								 *
								 */
								JSONObject innerObject = array.getJSONObject(jIndex);
								int strId = innerObject.getInt(TAG_CATEGORY_ID);
								String strName = innerObject.getString(TAG_CATEGORY_NAME);

								/**
								 * Getting Object from Main Object
								 */
								/**
								 * JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
								 * String phone = phoneObject.getString(Keys.KEY_MOBILE);
								 **/

								model_category.setCategoryID(strId);
								model_category.setCategoryName(strName);

								/**
								 * Adding name and phone concatenation in List...
								 */
								listCategory.add(model_category);

								listDataHeader.add(listCategory.get(jIndex).getCategoryName());
							}
						}
					}
				} else {

				}
			} catch (JSONException je) {
				Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			dialog.dismiss();

			// for (int i = 0; i < listCategory.size(); i++) {

			// 	int cateID = listCategory.get(i).getCategoryID();

			//	Log.i("Check cateID", "cateID = " + cateID);

				// listDataChild.put(listDataHeader.get(i), childOrderName);
				// listDataChild.put(listDataHeader.get(i), childOrderCategory);
				//if (cateID == childID) {

				//}

				/**
				if(cateID==1) {
					listDataChild.put(listDataHeader.get(i), arrChild);
				} else if(cateID==2) {
					listDataChild.put(listDataHeader.get(i), arrChild2);
				} else if(cateID==3) {
					listDataChild.put(listDataHeader.get(i), arrChild3);
				} else if(cateID==4) {
					listDataChild.put(listDataHeader.get(i), arrChild4);
				}
				**/
			//}

			/**
			 * Checking if List size if more than zero then
			 * Update ListView
			 */
			if(listCategory.size() > 0) {
				// adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(getApplicationContext(), "No Data Found Category", Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * Initial to AsyncTask Order
	 */
	class AsyncTaskOrder extends AsyncTask<Void, Void, Void> {

		private int jLoop;

		ProgressDialog dialog2;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			/**
			 * Progress Dialog for User Interaction
			 */
			dialog2 = new ProgressDialog(MainActivity.this);
			dialog2.setTitle("Please with...");
			dialog2.setMessage("Downloading...");
			dialog2.show();
		}

		@Nullable
		@Override
		protected Void doInBackground(Void... params) {
				/**
				 * Getting JSON Object from Web Using okHttp
				 */
				JSONObject jsonObject = JSONParser.makeHttp(url_list_order);

				try {
					/**
					 * Check Whether Its NULL???
					 */
					if (jsonObject != null) {
						/**
						 * Check Length...
						 */
						if (jsonObject.length() > 0) {
							/**
							 * Getting Array named "contacts" From MAIN Json Object
							 */
							JSONArray array = jsonObject.getJSONArray(TAG_ORDER);

							/**
							 * Check Length of Array...
							 */
							int lenArray = array.length();
							if (lenArray > 0) {
								for (jLoop = 0; jLoop < lenArray; jLoop++) {

									/**
									 * Creating Every time New Object
									 * and
									 * Adding into List
									 */
									ModelOrder model_order = new ModelOrder();

									/**
									 * Getting Inner Object from contacts array...
									 * and
									 * From that We will get Name of that Contact
									 *
									 */
									JSONObject innerObject = array.getJSONObject(jLoop);
									String strOrderName = innerObject.getString(TAG_ORDER_NAME);
									String strOrderPrice = innerObject.getString(TAG_ORDER_PRICE);
									String strOrderCategory = innerObject.getString(TAG_ORDER_CATEOGRY);

									/**
									 * Getting Object from Main Object
									 */
									/**
									 * JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
									 * String phone = phoneObject.getString(Keys.KEY_MOBILE);
									 **/

									model_order.setOrderCategory(strOrderCategory);
									model_order.setOrderName(strOrderName);
									model_order.setOrderPrice(strOrderPrice);

									/**
									 * Adding name and phone concatenation in List...
									 */
									listOrder.add(model_order);

								}
							}
						}
					} else {

					}
				} catch (JSONException je) {
					Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
				}

			return null;

		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			dialog2.dismiss();

			String[] orderName = new String[listCategory.size()];

			for (int j = 0; j < listCategory.size(); j++) {
				List<String> orderNameList = new ArrayList<String>();
				int cateIDCategory = listCategory.get(j).getCategoryID();
				Log.i("Check cateIDCategory", "cateIDCategory = " + cateIDCategory);

				// listDataChild.clear();

				for(int i = 0; i < listOrder.size(); i++) {
					String cate = listOrder.get(i).getOrderCategory();
					int cateIDOrder = Integer.parseInt(cate);
					Log.i("Check i", "i = " + i);
					Log.i("Check cateIDCategory", "cateIDCategoryInOrder = " + cateIDCategory);
					Log.i("Check cateIDOrder", "cateIDOrder = " + cateIDOrder);
					if (cateIDOrder==cateIDCategory) {
						String strOrderName = listOrder.get(i).getOrderName();
						Log.i("Check Ordername", "Ordername = " + strOrderName);
						orderName[j] = strOrderName;
						orderNameList.add(orderName[j]);
						Log.i("Check orderName", "orderName + "+"["+ j +"] = "+strOrderName);
						Log.i("Check ---------", "----------------------");
					}
				}
				Log.i("Check orderNameList", "orderNameList" + orderNameList);
				listDataChild.put(listDataHeader.get(j), orderNameList);
				Log.i("Check listDataChild", "listDataChild" + listDataChild);
			}

			initWidget();

				/**
				if(cate.equals(String.valueOf("1"))){
					arrChild.add(listOrder.get(i).getOrderName());
					arrChild.add(listOrder.get(i).getOrderCategory());
				} else if(cate.equals(String.valueOf("2"))){
					arrChild2.add(listOrder.get(i).getOrderName());
					arrChild2.add(listOrder.get(i).getOrderCategory());
				} else if(cate.equals(String.valueOf("3"))){
					arrChild3.add(listOrder.get(i).getOrderName());
					arrChild3.add(listOrder.get(i).getOrderCategory());
				} else if(cate.equals(String.valueOf("4"))){
					arrChild4.add(listOrder.get(i).getOrderName());
					arrChild4.add(listOrder.get(i).getOrderCategory());
				}
				**/
			// }

			//Log.i("Check Child", "child = " + child);

			/**
			 * Checking if List size if more than zero then
			 * Update ListView
			 */
			if(listOrder.size() > 0) {
				// adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(getApplicationContext(), "No Data Found Child", Toast.LENGTH_LONG).show();
			}
		}

	}

	private void initWidget() {
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		listAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
										int groupPosition, long id) {
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Log.i("Check Method", "onGroupExpand");
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Log.i("Check Method", "onGroupCollapse");
				listOrder.clear();
			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(
						getApplicationContext(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get(
								listDataHeader.get(groupPosition)).get(
								childPosition), Toast.LENGTH_SHORT)
						.show();
				return false;
			}
		});
	}

	/**
	 * Preparing the list data
	 **/

	private void prepareListData() {
		// listDataHeader = new ArrayList<String>();
		// listDataChild = new HashMap<String, List<String>>();

		// Log.i("Check size", "Size = " + listCategory.size());

		// Adding child data
		// listDataHeader.add("Top 250");
		// listDataHeader.add("Now Showing");
		// listDataHeader.add("Coming Soon..");
		// List<String> top250 = new ArrayList<>();
		// Adding child data
		List<String> top250 = new ArrayList<String>();
		top250.add("The Shawshank Redemption");
		top250.add("The Godfather");
		top250.add("The Godfather: Part II");
		top250.add("Pulp Fiction");
		top250.add("The Good, the Bad and the Ugly");
		top250.add("The Dark Knight");
		top250.add("12 Angry Men");

		List<String> top2502 = new ArrayList<String>();
		top2502.add("The Conjuring");
		top2502.add("Despicable Me 2");
		top2502.add("Turbo");
		top2502.add("Grown Ups 2");
		top2502.add("Red 2");
		top2502.add("The Wolverine");

		List<String> comingSoon = new ArrayList<String>();
		comingSoon.add("2 Guns");
		comingSoon.add("The Smurfs 2");
		comingSoon.add("The Spectacular Now");
		comingSoon.add("The Canyons");
		comingSoon.add("Europa Report");

		Log.i("Check top250","Top250 =" + top250);

		// listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		// listDataChild.put(listDataHeader.get(1), nowShowing);
		// listDataChild.put(listDataHeader.get(2), comingSoon);
		// listDataChild.put(listDataHeader.get(3), comingSoon);
		// listDataChild.put(listDataHeader.get(3), comingSoon);
	}
}
