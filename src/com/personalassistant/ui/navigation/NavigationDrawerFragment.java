package com.personalassistant.ui.navigation;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.personalassistant.R;
import com.personalassistant.model.User;
import com.personalassistant.model.UserRole;
import com.personalassistant.ui.location.LocationFragment;
import com.personalassistant.ui.main.MainFragment;
import com.personalassistant.ui.shedule.ScheduleFragment;

public class NavigationDrawerFragment extends Fragment {
	private static final int USER_ITEM = 0;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private NavigationDrawerCallbacks mCallbacks;

    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 1;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    
    private NavidationListItemAdapter adapter = null;
    
    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        
        adapter = new NavidationListItemAdapter(getActivity(), R.layout.global_navigation_row, createGlobalMenu()) {
			@Override
			protected int getSelectedPosition() {
				return mCurrentSelectedPosition - 1;
			}
        };
		mDrawerListView.setAdapter(adapter);
        
        TextView header = (TextView)getActivity().getLayoutInflater().inflate(R.layout.global_navigation_header, null);
        header.setText(User.getUser().getName());
        
        mDrawerListView.addHeaderView(header, null, false);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        selectItem(mCurrentSelectedPosition);
        setActionBarTitle(adapter.getItem(mCurrentSelectedPosition - 1).getName());
        
        return mDrawerListView;
    }
    
	private NavigationMenuItem[] createGlobalMenu() {
    	List<NavigationMenuItem> menuItems = new ArrayList<NavigationMenuItem>();
    	
    	menuItems.add(new NavigationMenuItem() {
			@Override
			public String getName() {
				return getString(R.string.main_item);
			}
			
			@Override
			public Fragment getFragment() {
				return new MainFragment();
			}

			@Override
			public int getIcon() {
				return R.drawable.home;
			}
		});
    	menuItems.add(new NavigationMenuItem() {
			@Override
			public String getName() {
				return getString(R.string.schedule_item);
			}
			
			@Override
			public Fragment getFragment() {
				return new ScheduleFragment();
			}

			@Override
			public int getIcon() {
				return R.drawable.calendar;
			}
		});
    	
    	if (User.getUser().getRole() == UserRole.LECTURER) {
    		menuItems.add(new NavigationMenuItem() {
    			@Override
    			public String getName() {
    				return getString(R.string.status_item);
    			}
    			
    			@Override
    			public Fragment getFragment() {
    				return new MainFragment();
    			}

				@Override
				public int getIcon() {
					return R.drawable.status;
				}
    		});
    		menuItems.add(new NavigationMenuItem() {
    			@Override
    			public String getName() {
    				return getString(R.string.location_item);
    			}
    			
    			@Override
    			public Fragment getFragment() {
    				return new LocationFragment();
    			}

				@Override
				public int getIcon() {
					return R.drawable.location;
				}
    		});
    	} else if (User.getUser().getRole() == UserRole.STUDENT) {
    		menuItems.add(new NavigationMenuItem() {
    			@Override
    			public String getName() {
    				return getString(R.string.lecturers_item);
    			}
    			
    			@Override
    			public Fragment getFragment() {
    				return new MainFragment();
    			}

				@Override
				public int getIcon() {
					return R.drawable.lecturers;
				}
    		});
    	}
    	
    	menuItems.add(new NavigationMenuItem() {
			@Override
			public String getName() {
				return getString(R.string.search_item);
			}
			
			@Override
			public Fragment getFragment() {
				return new MainFragment();
			}

			@Override
			public int getIcon() {
				return R.drawable.search;
			}
		});
    	
    	menuItems.add(new NavigationMenuItem() {
    		@Override
    		public String getName() {
    			return getString(R.string.settings_item);
    		}
    		
    		@Override
    		public Fragment getFragment() {
    			return new MainFragment();
    		}
    		
    		@Override
    		public int getIcon() {
    			return R.drawable.settings;
    		}
    	});
    	
    	return menuItems.toArray(new NavigationMenuItem[0]);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }
    
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                
                setActionBarTitle(adapter.getItem(mCurrentSelectedPosition - 1).getName());
                
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                
                setActionBarTitle(getString(R.string.app_name));
                
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null && mDrawerListView != null) {
        	if (position == USER_ITEM) {
        		// TODO : User
        	} else {
        		NavigationMenuItem item = (NavigationMenuItem)mDrawerListView.getItemAtPosition(position);
        		
        		if (item != null) {
        			mCallbacks.onNavigationDrawerItemSelected(item);
        		}
        	}
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }
    
    private void setActionBarTitle(String title) {
    	getActionBar().setTitle(title);
    }

    public static interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(NavigationMenuItem item);
    }
}
