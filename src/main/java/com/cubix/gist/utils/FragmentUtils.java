package com.cubix.gist.utils;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.widget.ImageView;

import com.cubix.gist.R;


/**
 * Created by imran.pyarali on 8/12/2015.
 */
public class FragmentUtils {

    public static void switchFragmentAdd(final Activity activity, final Fragment fragment, final boolean isAddToStack, final boolean isAnimate) {

        try {
            if (activity != null && !activity.isFinishing()/*&& !fragment.getClass().getName().equalsIgnoreCase(getCurrentFragmentTag(activity))*/) {

                GistUtils.hideSoftKey(activity);

                FragmentTransaction fragmentTransaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();

                if(isAnimate) {

                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                            R.anim.slide_in_left, R.anim.slide_out_right);
                }

                Fragment currentFragment = ((FragmentActivity)activity).getSupportFragmentManager().findFragmentById(R.id.main_content);

                if(currentFragment!= null) {

                    fragmentTransaction.hide(currentFragment);
                }

                if(isAddToStack){

                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
                }

                fragmentTransaction.add(R.id.main_content, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
                //fragmentTransaction.replace(R.id.main_content, fragment, fragment.getClass().getName()).commit();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void switchFragmentReplace(final Activity activity, final Fragment fragment, final boolean isAddToStack, final boolean isAnimate) {


        if (activity != null && !fragment.getClass().getName().equalsIgnoreCase(getCurrentFragmentTag(activity))) {

            GistUtils.hideSoftKey(activity);

            FragmentTransaction fragmentTransaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();

            if(isAnimate) {

                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right);
            }

            if(isAddToStack){

                fragmentTransaction.addToBackStack(fragment.getClass().getName());
            }

            //fragmentTransaction.add(R.id.main_content, fragment, fragment.getClass().getName()).commit();
            fragmentTransaction.replace(R.id.main_content, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
        }
    }

    public static void switchFragmentTransition(Activity activity, Fragment startFragement, Fragment endFragment,
                                                ImageView transitionImageView, String transition_name, boolean isAddToStack,
                                                boolean isAnimate) {

        GistUtils.hideSoftKey(activity);

        // if device os is lollipop implement transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            // set transitions
            /*startFragement.setSharedElementReturnTransition(TransitionInflater.from(
                    activity).inflateTransition(R.transition.change_image_trans));
            startFragement.setExitTransition(TransitionInflater.from(
                    activity).inflateTransition(android.R.transition.fade));*/

            endFragment.setSharedElementEnterTransition(TransitionInflater.from(
                    activity).inflateTransition(R.transition.change_image_trans));
            endFragment.setEnterTransition(TransitionInflater.from(
                    activity).inflateTransition(android.R.transition.fade));

            // send bunddle string  of transition_name in endfragment
            /*Bundle bundle = new Bundle();
            bundle.putString(GistConstants.TRANSITION_NAME, transition_name);
            endFragment.setArguments(bundle);*/

            FragmentTransaction fragmentTransaction =  ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();

            Fragment currentFragment = ((FragmentActivity)activity).getSupportFragmentManager().findFragmentById(R.id.main_content);

            if(currentFragment!= null) {

                fragmentTransaction.hide(currentFragment);
            }

            if(isAddToStack){
                fragmentTransaction.addToBackStack(endFragment.getClass().getName());
            }

            fragmentTransaction.addSharedElement(transitionImageView, transition_name);

            fragmentTransaction.replace(R.id.main_content, endFragment, endFragment.getClass().getName());
            //fragmentTransaction.add(contentId, endFragment,endFragment.getClass().getName());
          //  fragmentTransaction.replace(R.id.main_content, endFragment);

            fragmentTransaction.commitAllowingStateLoss();

        } else {

            switchFragmentAdd(activity, endFragment, isAddToStack, isAnimate);
        }
    }


    public static void removeFragment(Activity activity, Fragment fragment) {
        if (activity != null) {

            GistUtils.hideSoftKey(activity);

            FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
            manager.popBackStack();
        }
    }


    public static void removeFragmentt(Activity activity, Fragment fragment, ImageView transitionImageView, String transition_name) {
        if (activity != null) {

            GistUtils.hideSoftKey(activity);

            FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();

           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                // set transitions
                fragment.setSharedElementReturnTransition(TransitionInflater.from(
                        activity).inflateTransition(R.transition.change_image_trans));
                fragment.setExitTransition(TransitionInflater.from(
                        activity).inflateTransition(android.R.transition.fade));

                endFragment.setSharedElementEnterTransition(TransitionInflater.from(
                        activity).inflateTransition(R.transition.change_image_trans));
                endFragment.setEnterTransition(TransitionInflater.from(
                        activity).inflateTransition(android.R.transition.fade));

                // send bunddle string  of transition_name in endfragment
                Bundle bundle = new Bundle();
                bundle.putString(GistConstants.TRANSITION_NAME, transition_name);
                endFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction =  ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();

                Fragment currentFragment = ((FragmentActivity)activity).getSupportFragmentManager().findFragmentById(R.id.main_content);

                if(currentFragment!= null) {

                    fragmentTransaction.hide(currentFragment);
                }

                if(isAddToStack){
                    fragmentTransaction.addToBackStack(endFragment.getClass().getName());
                }

                fragmentTransaction.addSharedElement(transitionImageView, transition_name);

                //fragmentTransaction.add(R.id.main_content, fragment, fragment.getClass().getName()).commit();
                fragmentTransaction.add(R.id.main_content, endFragment,endFragment.getClass().getName());
                //  fragmentTransaction.replace(R.id.main_content, endFragment);

                fragmentTransaction.commit();

            }*/

            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
            manager.popBackStack();
        }
    }

    public static void homeFragment(Activity activity) {

        if (activity != null) {

            FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
            if (manager.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
                manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    public static Fragment getCurrentFragment(Activity activity) {

        if (activity != null) {

            FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {

                String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
                Fragment currentFragment = ((AppCompatActivity)activity).getSupportFragmentManager().findFragmentByTag(fragmentTag);

                return currentFragment;
            }
        }

        return null;
    }

    public static String getCurrentFragmentTag(Activity activity) {

        String currentTag = "";

        if (activity != null) {

            FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {

                String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
                Fragment currentFragment = ((AppCompatActivity)activity).getSupportFragmentManager().findFragmentByTag(fragmentTag);

                if( currentFragment != null ){

                    currentTag = currentFragment.getTag();
                }

            } else {

                Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_content);
                if (currentFragment != null) {

                    currentTag = currentFragment.getTag();
                }
            }
        }

        return currentTag;
    }

    public static void onBackPressed(Activity activity) {

        if (activity != null) {

            GistUtils.hideSoftKey(activity);
            activity.onBackPressed();
        }
    }
}
