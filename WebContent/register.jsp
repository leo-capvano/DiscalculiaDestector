<%@ page language="java"%>
<!DOCTYPE html>


<html>
    <head>
        <title>Registrazione</title>
        <%@ include file="WEB-INF/util/meta.jsp"%>
        <%@ include file="WEB-INF/util/header.jsp"%>
        
        <link rel="stylesheet" href="css/signup.css">
</head>

<body class="d-flex align-items-center">
    <div class="container overflow-hidden">

        <div class="row h-100 align-items-center justify-content-center">
            <div id="selector" class="col-12 col-sm-8 my-4">

                <div class="display-4 text-center text-white text-uppercase mb-4">
                    Io sono
                </div>

                <div class="row">
                    <div class="col-12 col-md-6">
                        <div class="card hoverable mt-4">
                            <div class="card-body">
                                <div class="row align-items-center" data-type="student">
                                    <div class="col-6 col-md-12">
                                        <img class="d-block mx-auto my-2 w-75" src="images/student.svg" alt="student">
                                    </div>
                                    <div class="col-6 col-md-12">
                                        <div class="h5 text-center mt-4">Uno studente</div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="small text-secondary text-center">Icons made by <a class="text-secondary"
                                        href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from
                                    <a class="text-secondary" href="https://www.flaticon.com/"
                                        title="Flaticon">Flaticon</a></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-md-6">
                        <div class="card hoverable mt-4" data-type="teacher">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col-6 col-md-12"><img class="d-block mx-auto my-2 w-75"
                                            src="images/blackboard.svg" alt="teacher"></div>
                                    <div class="col-6 col-md-12">
                                        <div class="h5 text-center mt-4">Un insegnante</div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="small text-secondary text-center">Icons made by <a class="text-secondary"
                                        href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from
                                    <a class="text-secondary" href="https://www.flaticon.com/"
                                        title="Flaticon">Flaticon</a></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col text-center my-4">
                        <a class="text-white" href="login.jsp">Ho già un account</a>
                    </div>
                </div>
            </div>

            <div id="signup-div" class="col-12 col-sm-8 my-4 d-none">
                <div class="card mx-auto">

                    <div class="card-header"><a id="back-button" href="#">Indietro</a></div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-12 col-md-6">
                                <img src="images/login.png" alt="login_image" class="rounded mx-auto d-block w-100">
                            </div>
                            <div class="col-12 col-md-6">
                                <form id="formControl" class="needs-validation" novalidate>

                                    <h4 class="pb-4 text-center" data-role="title">Registrati</h4>

                                    <div class="alert alert-danger alert-dismissible d-none" role="alert">
                                        Hello
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>

                                    <div class="row">
                                        <div class="col-12 col-lg-6">
                                            <div class="form-group">
                                                <div class="form-label-group">
                                                    <label for="name">Nome</label>
                                                    <input type="text" name="name" id="name" class="form-control" placeholder="Nome" autofocus="autofocus" required>
                                                    <div class="invalid-feedback">Inserisci un nome</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 col-lg-6">
                                            <div class="form-group">
                                                <div class="form-label-group">
                                                    <label for="surname">Cognome</label>
                                                    <input type="text" name="surname" id="surname" class="form-control" placeholder="Cognome" required>
                                                    <div class="invalid-feedback">Inserisci un cognome</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="form-label-group">
                                            <label for="inputEmail">Indirizzo email</label>
                                            <input type="email" name="email" id="inputEmail" class="form-control"
                                                placeholder="Email address" required>
                                            <div class="invalid-feedback">Inserisci un email valida</div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-12 col-lg-6">
                                            <div class="form-group">
                                                <div class="form-label-group">
                                                    <label for="inputPassword">Password</label>
                                                    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                                                    <div class="invalid-feedback">Digita la password</div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-12 col-lg-6">
                                            <div class="form-group">
                                                <div class="form-label-group">
                                                    <label for="repeatPassword">Ripeti password</label>
                                                    <input type="password" name="repeatPassword" id="repeatPassword" class="form-control"
                                                        placeholder="Password" required>
                                                    <div class="invalid-feedback">Le due password devono coincidere</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="toHide" class="form-group d-none">
                                        <div class="form-label-group">
                                            <label for="institutes">Istituto in cui si insegna <img class="custom-loading" id="institute" src="images/loading-blue.svg" alt="loading"></label>
                                            <select id="institutes" class="form-control">
                                                <option value="" selected hidden>Scegli</option>
                                            </select>
                                            <div class="invalid-feedback">Seleziona un istituto</div>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group form-check">
    									<input type="checkbox" class="form-check-input" id="tos" required>
    									<label class="form-check-label" for="tos">Accetto i <a href="#" data-toggle="modal" data-target="#tosModal">termini di servizio</a></label>
    									<div class="invalid-feedback">Devi accettare i termini di servizio</div>
  									</div>

                                    <button id="btnControl" class="btn btn-dark btn-block">Registrati</button>
                                </form>
                                <div class="text-center pt-2">
                                    <a class="d-block small" href="login.jsp">Hai gi&agrave; un account?</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div id="confirm-div" class="col-12 col-sm-8 my-4 d-none">
                <div class="card mx-auto">
                    <div class="card-header">Ti abbiamo inviato una mail</div>
                    <div class="card-body">
                        <div class="row align-items-center">
                            <div class="col-12 col-md-6 p-4">
                                <img src="images/email-sent.svg" alt="email_sent" class="rounded mx-auto d-block w-75 p-4">
                            </div>
                            <div class="col-12 col-md-6">
                                <div class="display-4 text-center my-4">Conferma l'email</div>
                                <div>
                                    <b>Hey <span id="account-name">user</span></b>, ci sei quasi!<br>
                                    Controlla la tua casella di posta e verifica il tuo account per procere.
                                </div>
                                <a class="btn btn-block btn-primary my-4" href="login.jsp">Fatto!</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="modal fade" id="tosModal" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
				<div class="modal-dialog modal-dialog-scrollable" role="document">
    				<div class="modal-content">
      					<div class="modal-header">
        					<h5 class="modal-title" id="modalTitle">Termini di servizio</h5>
        					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          						<span aria-hidden="true">&times;</span>
        					</button>
      					</div>
						<div class="modal-body">
							Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ac porta arcu. Donec fermentum eros sit amet feugiat sollicitudin. Suspendisse tristique et arcu at facilisis. Etiam varius arcu sed nisi facilisis, mollis commodo lacus hendrerit. Mauris eget eleifend odio. Aliquam pharetra vel ante a venenatis. Integer fermentum feugiat nibh, non pretium massa tincidunt non. Mauris id luctus ligula, at lacinia velit. Maecenas pulvinar accumsan massa at sagittis. Vivamus ac elit nulla. Vestibulum id venenatis lorem, id accumsan erat. Donec egestas, leo ut sodales aliquet, enim tellus mattis quam, vitae volutpat augue enim ac arcu. In pellentesque justo at congue venenatis. Nunc non libero quam. Quisque interdum nunc eu nisl cursus posuere.
							
							Suspendisse a purus nec ipsum bibendum maximus. Donec id faucibus elit, quis sodales augue. Fusce porta ornare mauris sed gravida. Vestibulum odio mauris, cursus et odio ac, varius gravida erat. Pellentesque eleifend libero at mattis aliquet. Donec pellentesque placerat rutrum. Suspendisse potenti. Donec nec nunc sit amet lacus auctor pulvinar id nec velit. Vivamus a fermentum massa. Duis auctor felis vitae turpis dictum, sit amet interdum urna interdum. Fusce nisl felis, porta nec sapien ac, rhoncus cursus mauris. Vestibulum tempor non lacus vitae pharetra.
							
							Donec dictum lorem sed risus dictum imperdiet. Integer accumsan auctor ex, eu semper purus lacinia a. Mauris vulputate orci id venenatis tincidunt. Pellentesque nisl quam, porttitor id lectus in, tempor volutpat mauris. Proin eleifend dui vel nisl vestibulum, non tincidunt nisl dictum. In convallis lacus ut nisl congue scelerisque. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque volutpat bibendum lorem, a molestie nisl facilisis non.
							
							In vitae libero semper, consectetur erat sit amet, mollis ligula. Quisque suscipit pulvinar risus, eget ornare nunc porttitor vitae. Cras dolor augue, lobortis at euismod eu, fringilla id neque. Suspendisse neque lacus, imperdiet luctus rhoncus sit amet, gravida quis erat. Vivamus bibendum faucibus pulvinar. Sed sapien dolor, ultricies nec lacinia eget, pulvinar et tellus. Etiam placerat nisl turpis, nec ultrices augue molestie non. Mauris eu turpis nec nisl ornare mollis vitae id augue. Etiam ipsum dolor, ullamcorper nec bibendum sit amet, gravida eget enim. Nullam dapibus massa quis sapien sodales fringilla. Proin elementum venenatis dui quis placerat. Pellentesque porttitor sem purus, euismod fermentum augue malesuada sit amet.
							
							Sed faucibus urna risus, placerat lacinia nunc sodales sed. Nunc sollicitudin, erat vel venenatis mollis, leo ipsum vestibulum mauris, sed fermentum ipsum nisl id ex. Suspendisse potenti. Pellentesque ac tellus vulputate, sagittis urna et, pulvinar lorem. Proin pharetra massa sem, quis euismod sem sagittis ac. Aliquam erat volutpat. Suspendisse interdum consectetur quam, sed iaculis nisi ultrices sed. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam mollis quam nisi, vitae hendrerit ante interdum ut. In aliquet semper finibus. Cras facilisis in est sit amet imperdiet. Mauris eget massa finibus, facilisis libero ut, aliquam dolor.
						</div>
      					<div class="modal-footer">
        					<button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
      					</div>
    				</div>
  				</div>
			</div>
			
        </div>
    </div>
    <script src="js/signup.js"></script>

</body>

</html>