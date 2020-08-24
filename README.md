# CoroutinePractice
コルーチン勉強用に作ったタイマープログラム。5秒カウントダウンを行い、その後、カウントダウンとカウントアップを同時に動かす例。
実際の動きは最終行のgif参照（gifにしたせいでカウントが早くなってます）

Fragment内でcoroutineを使い非同期処理を行う場合。
viewModel内の処理を非同期で実行したいのでviewModelScopeを使います。
直列で実行したい場合はlaunch内で同期処理を書くのと同様に単純に処理を呼び出します。そうすることで上から順に順次実行されます。
並列処理を行いたい場合はasyncで囲みます。asyncで囲んだメソッドに関しては並列で実行されます。下記のケースだとcountUpTimerとcountDownTimerが並列で動きます。
    
        button.setOnClickListener {
            viewModel.viewModelScope.launch {
            	  // 直列処理
                viewModel.countDownPreparationTimer()

				        // 並列処理
                async { viewModel.countUpTimer() }
                async { viewModel.countDownTimer() }
            }

        }
        
        
viewModel内の処理。launchで呼び出されるメソッドになるのでsuspend修飾子を付けます。

      suspend fun countUpTimer() {
            countUpTime()
      }
      
      suspend fun countDownPreparationTimer() {
            countDownPreparationTime()
      }
      
      suspend fun countDownTimer() {
            countDownTime()
      }
    	
    	
    suspend fun countUpTime() {
        withContext(Dispatchers.IO) {
            delay(1000L)
            mCountUpTimer.let {
                it.postValue(it.value?.plus(1))
            }
        }
    }
    
	下記メソッドはRepository内で定義するメソッドを想定したもの。今回はRepositoryを作っていないのでViewModel内で定義しています。
	Mainスレッドで実行したくないのでwithContextを使いIOに切り替えています。
  
    suspend fun countDownTime() {
        withContext(Dispatchers.IO) {
            delay(1000L)
            mCountDownTimer.let {
                it.postValue(it.value?.minus(1))
            }
        }
    }

    suspend fun countDownPreparationTime() {
        withContext(Dispatchers.IO) {
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
        }
    }

 ViewModel内でcoroutineを使い非同期で実行する場合。この作り方の方が一般的だと思われる。
 Fragmentでメソッド呼び出し。
 
    viewModel.countup()

 ViewModel内でviewModelScopeを使いcoroutine起動。
 
    fun countup() {
        viewModelScope.launch {
            countDownPreparationTime()
            async { countUpTime() }
            async { countDownTime() }
        }
    }

![Videotogif](https://user-images.githubusercontent.com/37768294/91010715-f5c52700-e61d-11ea-99e6-d94bc31faa7e.gif)
