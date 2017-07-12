为了避免重复造轮子，我们一般都会封装一个通用的控件，比如这次，项目中需要用到比较多的 popupwindow ，如果需要一个个写，那么依旧会累死人，而且还是无用功，无意义，所以，封装一个通用的，除了让同事看了直刷666之外，自己还省了很多事情。

# 1、如何使用

那么，一般我们配置一个 PopupWindow 正常步骤需要多少代码呢？如下：


    PopupWindow popupWindow = new PopupWindow(this);
        View contentview = LayoutInflater.from(this).inflate(R.layout.popup_calendar,null);
        popupWindow =
                new PopupWindow(contentview,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        true);
        //设置取消
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //设置位置
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        popupWindow.showAtLocation(rootview,Gravity.CENTER,0,0);


一般我们需要实现上面的基本代码，PopupWindow 才能跑起来，然后我们还需要添加动画，监听back键等等，然后，另外一个需要用到的时候，又得重复写，真的让人很绝望，这个时候，封装的思想就从脑袋冒出来了，那么，封装之后，怎么样的呢？如下：

    CustomPopupWindow popupWindow = new CustomPopupWindow.Builder()
                        .setContext(this) //设置 context
                        .setContentView(R.layout.popup_calendar) //设置布局文件
                        .setwidth(LinearLayout.LayoutParams.WRAP_CONTENT) //设置宽度，由于我已经在布局写好，这里就用 wrap_content就好了
                        .setheight(LinearLayout.LayoutParams.WRAP_CONTENT) //设置高度
                        .setFouse(true)  //设置popupwindow 是否可以获取焦点
                        .setOutSideCancel(true) //设置点击外部取消
                        .setAnimationStyle(R.style.popup_anim_style) //设置popupwindow动画
                        .builder() //
                        .showAtLocation(R.layout.activity_calendar, Gravity.CENTER,0,0); //设置popupwindow居中显示



还有一种常见的情况，我们常用 popupwindow 作用 dialog，那么里面有 button 处理相应的逻辑。那如何想获取 PopupWindow 里面的控件怎么办？为了方便调用，这里我也采用用 id 的形式，所以，调用只要这样即可：


    mMonthPicker = (PickerView) popupWindow.getItemView(R.id.picker_month);


然后就可以用 mMonthPicker 这个 view 搞事情了。

这样就把 contentview 中的控件取出来使用了，只要知道 id 就可以了，是不是方便了很多，都挺简单的，大家自己封装一边就ok全明白了

# CustomPopupWindow 完整代码：

    public  class CustomPopupWindow {
    private PopupWindow mPopupWindow;
    private View contentview;
    private  Context mContext;
    public CustomPopupWindow(Builder builder) {
        mContext = builder.context;
        contentview = LayoutInflater.from(mContext).inflate(builder.contentviewid,null);
        mPopupWindow =
                new PopupWindow(contentview,builder.width,builder.height,builder.fouse);

        //需要跟 setBackGroundDrawable 结合
        mPopupWindow.setOutsideTouchable(builder.outsidecancel);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mPopupWindow.setAnimationStyle(builder.animstyle);



    }



    /**
     * popup 消失
     */
    public void dismiss(){
        if (mPopupWindow != null){
            mPopupWindow.dismiss();
        }
    }

    /**
     * 根据id获取view
     * @param viewid
     * @return
     */
    public View getItemView(int viewid){
        if (mPopupWindow != null){
            return this.contentview.findViewById(viewid);
        }
        return null;
    }

    /**
     * 根据父布局，显示位置
     * @param rootviewid
     * @param gravity
     * @param x
     * @param y
     * @return
     */
    public CustomPopupWindow showAtLocation(int rootviewid,int gravity,int x,int y){
        if (mPopupWindow != null){
            View rootview = LayoutInflater.from(mContext).inflate(rootviewid,null);
            mPopupWindow.showAtLocation(rootview,gravity,x,y);
        }
        return this;
    }

    /**
     * 根据id获取view ，并显示在该view的位置
     * @param targetviewId
     * @param gravity
     * @param offx
     * @param offy
     * @return
     */
    public CustomPopupWindow showAsLaction(int targetviewId,int gravity,int offx,int offy){
        if (mPopupWindow != null){
            View targetview = LayoutInflater.from(mContext).inflate(targetviewId,null);
            mPopupWindow.showAsDropDown(targetview,gravity,offx,offy);
        }
        return this;
    }

    /**
     * 显示在 targetview 的不同位置
     * @param targetview
     * @param gravity
     * @param offx
     * @param offy
     * @return
     */
    public CustomPopupWindow showAsLaction(View targetview,int gravity,int offx,int offy){
        if (mPopupWindow != null){
            mPopupWindow.showAsDropDown(targetview,gravity,offx,offy);
        }
        return this;
    }


    /**
     * 根据id设置焦点监听
     * @param viewid
     * @param listener
     */
    public void setOnFocusListener(int viewid,View.OnFocusChangeListener listener){
        View view = getItemView(viewid);
        view.setOnFocusChangeListener(listener);
    }


    /**
     * builder 类
     */
    public static class Builder{
        private int contentviewid;
        private int width;
        private int height;
        private boolean fouse;
        private boolean outsidecancel;
        private int animstyle;
        private Context context;

       public Builder setContext(Context context){
           this.context = context;
           return this;
       }


        public Builder setContentView(int contentviewid){
            this.contentviewid = contentviewid;
            return this;
        }

        public Builder setwidth(int width){
            this.width = width;
            return this;
        }
        public Builder setheight(int height){
            this.height = height;
            return this;
        }

        public Builder setFouse(boolean fouse){
            this.fouse = fouse;
            return this;
        }

        public Builder setOutSideCancel(boolean outsidecancel){
            this.outsidecancel = outsidecancel;
            return this;
        }
        public Builder setAnimationStyle(int animstyle){
            this.animstyle = animstyle;
            return this;
        }



        public CustomPopupWindow builder(){
           return new CustomPopupWindow(this);
        }
    }
}