(ml) developer@developer:~$ python -m pip install pytorch_forecasting                                 
Collecting pytorch_forecasting
  Using cached pytorch_forecasting-0.10.1-py3-none-any.whl (127 kB)
Collecting matplotlib (from pytorch_forecasting)
  Obtaining dependency information for matplotlib from https://files.pythonhosted.org/packages/39/fc/fca496a890274b6628e310816710718d8184ede99956160c05a017789acc/matplotlib-3.8.0-cp311-cp311-manylinux_2_17_aarch64.manylinux2014_aarch64.whl.metadata
  Downloading matplotlib-3.8.0-cp311-cp311-manylinux_2_17_aarch64.manylinux2014_aarch64.whl.metadata (5.8 kB)
Collecting optuna<3.0.0,>=2.3.0 (from pytorch_forecasting)
  Using cached optuna-2.10.1-py3-none-any.whl (308 kB)
Collecting pandas<2.0.0,>=1.3.0 (from pytorch_forecasting)
  Using cached pandas-1.5.3-cp311-cp311-manylinux_2_17_aarch64.manylinux2014_aarch64.whl (11.4 MB)
Collecting pytorch-lightning<2.0.0,>=1.2.4 (from pytorch_forecasting)
  Using cached pytorch_lightning-1.9.5-py3-none-any.whl (829 kB)
Collecting scikit-learn<1.1,>=0.24 (from pytorch_forecasting)
  Using cached scikit-learn-1.0.2.tar.gz (6.7 MB)
  Installing build dependencies ... done
  Getting requirements to build wheel ... done
  Preparing metadata (pyproject.toml) ... error
  error: subprocess-exited-with-error
  
  × Preparing metadata (pyproject.toml) did not run successfully.
  │ exit code: 1
  ╰─> [820 lines of output]
      Partial import of sklearn during the build process.
      setup.py:128: DeprecationWarning:
      
        `numpy.distutils` is deprecated since NumPy 1.23.0, as a result
        of the deprecation of `distutils` itself. It will be removed for
        Python >= 3.12. For older Python versions it will remain present.
        It is recommended to use `setuptools < 60.0` for those Python versions.
        For more details, see:
          https://numpy.org/devdocs/reference/distutils_status_migration.html
      
      
        from numpy.distutils.command.build_ext import build_ext  # noqa
      INFO: C compiler: aarch64-linux-gnu-gcc -Wsign-compare -DNDEBUG -g -fwrapv -O2 -Wall -g -fstack-protector-strong -Wformat -Werror=format-security -g -fwrapv -O2 -fPIC
      
      INFO: compile options: '-c'
      INFO: aarch64-linux-gnu-gcc: test_program.c
      INFO: aarch64-linux-gnu-gcc objects/test_program.o -o test_program
      INFO: C compiler: aarch64-linux-gnu-gcc -Wsign-compare -DNDEBUG -g -fwrapv -O2 -Wall -g -fstack-protector-strong -Wformat -Werror=format-security -g -fwrapv -O2 -fPIC
      
      INFO: compile options: '-c'
      extra options: '-fopenmp'
      INFO: aarch64-linux-gnu-gcc: test_program.c
      INFO: aarch64-linux-gnu-gcc objects/test_program.o -o test_program -fopenmp
      Compiling sklearn/__check_build/_check_build.pyx because it changed.
      Compiling sklearn/preprocessing/_csr_polynomial_expansion.pyx because it changed.
      Compiling sklearn/cluster/_dbscan_inner.pyx because it changed.
      Compiling sklearn/cluster/_hierarchical_fast.pyx because it changed.
      Compiling sklearn/cluster/_k_means_common.pyx because it changed.
      Compiling sklearn/cluster/_k_means_lloyd.pyx because it changed.
      Compiling sklearn/cluster/_k_means_elkan.pyx because it changed.
      Compiling sklearn/cluster/_k_means_minibatch.pyx because it changed.
      Compiling sklearn/datasets/_svmlight_format_fast.pyx because it changed.
      Compiling sklearn/decomposition/_online_lda_fast.pyx because it changed.
      Compiling sklearn/decomposition/_cdnmf_fast.pyx because it changed.
      Compiling sklearn/ensemble/_gradient_boosting.pyx because it changed.
      Compiling sklearn/ensemble/_hist_gradient_boosting/_gradient_boosting.pyx because it changed.
      Compiling sklearn/ensemble/_hist_gradient_boosting/histogram.pyx because it changed.
      Compiling sklearn/ensemble/_hist_gradient_boosting/splitting.pyx because it changed.
      Compiling sklearn/ensemble/_hist_gradient_boosting/_binning.pyx because it changed.
      Compiling sklearn/ensemble/_hist_gradient_boosting/_predictor.pyx because it changed.
      Compiling sklearn/ensemble/_hist_gradient_boosting/_loss.pyx because it changed.
      Compiling sklearn/ensemble/_hist_gradient_boosting/_bitset.pyx because it changed.
      Compiling sklearn/ensemble/_hist_gradient_boosting/common.pyx because it changed.
      Compiling sklearn/ensemble/_hist_gradient_boosting/utils.pyx because it changed.
      Compiling sklearn/feature_extraction/_hashing_fast.pyx because it changed.
      Compiling sklearn/manifold/_utils.pyx because it changed.
      Compiling sklearn/manifold/_barnes_hut_tsne.pyx because it changed.
      Compiling sklearn/metrics/cluster/_expected_mutual_info_fast.pyx because it changed.
      Compiling sklearn/metrics/_pairwise_fast.pyx because it changed.
      Compiling sklearn/metrics/_dist_metrics.pyx because it changed.
      Compiling sklearn/neighbors/_ball_tree.pyx because it changed.
      Compiling sklearn/neighbors/_kd_tree.pyx because it changed.
      Compiling sklearn/neighbors/_partition_nodes.pyx because it changed.
      Compiling sklearn/neighbors/_quad_tree.pyx because it changed.
      Compiling sklearn/tree/_tree.pyx because it changed.
      Compiling sklearn/tree/_splitter.pyx because it changed.
      Compiling sklearn/tree/_criterion.pyx because it changed.
      Compiling sklearn/tree/_utils.pyx because it changed.
      Compiling sklearn/utils/sparsefuncs_fast.pyx because it changed.
      Compiling sklearn/utils/_cython_blas.pyx because it changed.
      Compiling sklearn/utils/arrayfuncs.pyx because it changed.
      Compiling sklearn/utils/murmurhash.pyx because it changed.
      Compiling sklearn/utils/_fast_dict.pyx because it changed.
      Compiling sklearn/utils/_openmp_helpers.pyx because it changed.
      Compiling sklearn/utils/_seq_dataset.pyx because it changed.
      Compiling sklearn/utils/_weight_vector.pyx because it changed.
      Compiling sklearn/utils/_random.pyx because it changed.
      Compiling sklearn/utils/_logistic_sigmoid.pyx because it changed.
      Compiling sklearn/utils/_readonly_array_wrapper.pyx because it changed.
      Compiling sklearn/utils/_typedefs.pyx because it changed.
      Compiling sklearn/svm/_newrand.pyx because it changed.
      Compiling sklearn/svm/_libsvm.pyx because it changed.
      Compiling sklearn/svm/_liblinear.pyx because it changed.
      Compiling sklearn/svm/_libsvm_sparse.pyx because it changed.
      Compiling sklearn/linear_model/_cd_fast.pyx because it changed.
      Compiling sklearn/linear_model/_sgd_fast.pyx because it changed.
      Compiling sklearn/linear_model/_sag_fast.pyx because it changed.
      Compiling sklearn/_isotonic.pyx because it changed.
      warning: sklearn/metrics/_dist_metrics.pxd:12:64: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:22:65: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:31:79: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:35:79: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:54:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:57:52: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:64:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:66:67: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:61:73: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:62:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:63:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:84:72: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:89:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:57:45: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:58:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:59:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:60:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:49:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:87:61: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:119:56: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:137:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:139:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:160:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:161:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:72:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:91:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:94:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:95:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:96:80: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
              if n_used_bins <= 1:
                  free(cat_infos)
                  return
      
              qsort(cat_infos, n_used_bins, sizeof(categorical_info),
                    compare_cat_infos)
                    ^
      ------------------------------------------------------------
      
      sklearn/ensemble/_hist_gradient_boosting/splitting.pyx:920:14: Cannot assign type 'int (const void *, const void *) except? -1 nogil' to 'int (*)(const void *, const void *) noexcept nogil'. Exception values are incompatible. Suggest adding 'noexcept' to type 'int (const void *, const void *) except? -1 nogil'.
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/ensemble/_hist_gradient_boosting/splitting.pyx
      warning: sklearn/linear_model/_sgd_fast.pyx:26:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/linear_model/_sgd_fast.pyx:27:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/linear_model/_sgd_fast.pyx:28:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/linear_model/_sgd_fast.pyx:29:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/linear_model/_sgd_fast.pyx:32:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/linear_model/_sgd_fast.pyx:33:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/linear_model/_sgd_fast.pyx:34:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/linear_model/_sgd_fast.pyx:35:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/linear_model/_sgd_fast.pyx:36:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/linear_model/_sgd_fast.pyx:37:0: The 'DEF' statement is deprecated and will be removed in a future Cython version. Consider using global variables, constants, and in-place literals instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/neighbors/_quad_tree.pxd:72:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:91:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:94:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:95:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:96:80: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:12:64: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:22:65: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:31:79: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:35:79: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:54:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:57:52: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:64:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:66:67: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:295:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:303:52: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:334:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:338:67: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:433:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:437:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:440:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:443:74: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:472:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:481:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:484:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:487:74: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:510:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:543:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:573:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:581:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:584:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:587:74: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:631:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:639:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:642:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:645:74: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:694:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:710:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:713:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:716:74: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:739:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:761:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:784:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:810:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:840:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:864:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:889:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:914:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:938:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:962:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:986:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:1020:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:1026:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:1029:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:1032:74: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pyx:1132:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:562:66: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:570:49: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:632:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:1122:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:1131:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:1716:78: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_ball_tree.pyx:104:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_ball_tree.pyx:120:82: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_ball_tree.pyx:131:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:12:64: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:22:65: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:31:79: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:35:79: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:54:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:57:52: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:64:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:66:67: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
              # determine number of levels in the tree, and from this
              # the number of nodes in the tree.  This results in leaf nodes
              # with numbers of points between leaf_size and 2 * leaf_size
              self.n_levels = int(
                  np.log2(fmax(1, (n_samples - 1) / self.leaf_size)) + 1)
              self.n_nodes = (2 ** self.n_levels) - 1
                                                  ^
      ------------------------------------------------------------
      
      sklearn/neighbors/_binary_tree.pxi:982:44: Cannot assign type 'double' to 'ITYPE_t'
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/neighbors/_ball_tree.pyx
      warning: sklearn/neighbors/_binary_tree.pxi:562:66: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:570:49: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:632:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:1122:58: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:1131:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_binary_tree.pxi:1716:78: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_kd_tree.pyx:86:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_kd_tree.pyx:147:82: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:12:64: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:22:65: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:31:79: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:35:79: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:54:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:57:52: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:64:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/metrics/_dist_metrics.pxd:66:67: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
              # determine number of levels in the tree, and from this
              # the number of nodes in the tree.  This results in leaf nodes
              # with numbers of points between leaf_size and 2 * leaf_size
              self.n_levels = int(
                  np.log2(fmax(1, (n_samples - 1) / self.leaf_size)) + 1)
              self.n_nodes = (2 ** self.n_levels) - 1
                                                  ^
      ------------------------------------------------------------
      
      sklearn/neighbors/_binary_tree.pxi:982:44: Cannot assign type 'double' to 'ITYPE_t'
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/neighbors/_kd_tree.pyx
      warning: sklearn/neighbors/_quad_tree.pxd:72:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:91:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:94:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:95:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:96:80: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pyx:116:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pyx:305:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pyx:464:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pyx:559:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pyx:571:70: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:49:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:87:61: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:119:56: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:137:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:139:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:160:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:161:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:61:73: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:62:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:63:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:84:72: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:89:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:57:45: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:58:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:59:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:60:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
          def __cinit__(self, int n_dimensions, int verbose):
              """Constructor."""
              # Parameters of the tree
              self.n_dimensions = n_dimensions
              self.verbose = verbose
              self.n_cells_per_cell = 2 ** self.n_dimensions
                                        ^
      ------------------------------------------------------------
      
      sklearn/neighbors/_quad_tree.pyx:56:34: Cannot assign type 'double' to 'SIZE_t'
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/neighbors/_quad_tree.pyx
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
              free_problem(problem)
              free_parameter(param)
              raise ValueError(error_msg)
      
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_liblinear.pyx:55:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
              free_parameter(param)
              raise ValueError(error_msg)
      
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
          blas_functions.axpy = _axpy[double]
                                     ^
      ------------------------------------------------------------
      
      sklearn/svm/_liblinear.pyx:56:31: Cannot assign type 'void (int, double, double *, int, double *, int) except * nogil' to 'axpy_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'void (int, double, double *, int, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
              raise ValueError(error_msg)
      
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
          blas_functions.axpy = _axpy[double]
          blas_functions.scal = _scal[double]
                                     ^
      ------------------------------------------------------------
      
      sklearn/svm/_liblinear.pyx:57:31: Cannot assign type 'void (int, double, double *, int) except * nogil' to 'scal_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'void (int, double, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
      
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
          blas_functions.axpy = _axpy[double]
          blas_functions.scal = _scal[double]
          blas_functions.nrm2 = _nrm2[double]
                                     ^
      ------------------------------------------------------------
      
      sklearn/svm/_liblinear.pyx:58:31: Cannot assign type 'double (int, double *, int) except * nogil' to 'nrm2_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int) except * nogil'.
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/svm/_liblinear.pyx
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
          if error_msg:
              # for SVR: epsilon is called p in libsvm
              error_repl = error_msg.decode('utf-8').replace("p < 0", "epsilon < 0")
              raise ValueError(error_repl)
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_libsvm.pyx:191:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
                             class_weight_label.data, class_weight.data)
          model = set_model(&param, <int> nSV.shape[0], SV.data, SV.shape,
                            support.data, support.shape, sv_coef.strides,
                            sv_coef.data, intercept.data, nSV.data, probA.data, probB.data)
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_libsvm.pyx:355:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
                            sv_coef.data, intercept.data, nSV.data,
                            probA.data, probB.data)
      
          cdef np.npy_intp n_class = get_nr(model)
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_libsvm.pyx:461:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
              n_class = 1
          else:
              n_class = get_nr(model)
              n_class = n_class * (n_class - 1) // 2
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_libsvm.pyx:567:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
          if error_msg:
              raise ValueError(error_msg)
      
          cdef np.ndarray[np.float64_t, ndim=1, mode='c'] target
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_libsvm.pyx:711:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/svm/_libsvm.pyx
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
          if error_msg:
              free_problem(problem)
              free_param(param)
              raise ValueError(error_msg)
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_libsvm_sparse.pyx:153:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
                                sv_coef.data, intercept.data,
                                nSV.data, probA.data, probB.data)
          #TODO: use check_model
          dec_values = np.empty(T_indptr.shape[0]-1)
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_libsvm_sparse.pyx:284:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
          #TODO: use check_model
          cdef np.npy_intp n_class = get_nr(model)
          cdef int rv
          dec_values = np.empty((T_indptr.shape[0]-1, n_class), dtype=np.float64)
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_libsvm_sparse.pyx:343:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
              n_class = get_nr(model)
              n_class = n_class * (n_class - 1) // 2
      
          dec_values = np.empty((T_indptr.shape[0] - 1, n_class), dtype=np.float64)
          cdef BlasFunctions blas_functions
          blas_functions.dot = _dot[double]
                                   ^
      ------------------------------------------------------------
      
      sklearn/svm/_libsvm_sparse.pyx:412:29: Cannot assign type 'double (int, double *, int, double *, int) except * nogil' to 'dot_func'. Exception values are incompatible. Suggest adding 'noexcept' to type 'double (int, double *, int, double *, int) except * nogil'.
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/svm/_libsvm_sparse.pyx
      warning: sklearn/tree/_criterion.pxd:57:45: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:58:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:59:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:60:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:61:73: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:62:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:63:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:84:72: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:89:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:57:45: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:82:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:89:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:96:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:278:76: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:344:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:371:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:398:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:740:45: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:784:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:795:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:806:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:1028:45: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:1078:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:1109:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pyx:1137:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:49:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:87:61: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:119:56: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:137:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:139:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:160:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:161:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:72:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:91:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:94:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:95:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:96:80: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:84:72: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:89:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:57:45: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:58:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:59:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:60:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:61:73: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:62:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:63:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pyx:180:72: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pyx:210:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pyx:264:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pyx:578:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pyx:1096:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pyx:1326:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:49:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:87:61: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:119:56: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:137:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:139:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:160:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:161:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:72:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:91:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:94:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:95:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:96:80: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
          if not is_samples_sorted[0]:
              n_samples = end - start
              memcpy(sorted_samples + start, samples + start,
                     n_samples * sizeof(SIZE_t))
              qsort(sorted_samples + start, n_samples, sizeof(SIZE_t),
                    compare_SIZE_t)
                    ^
      ------------------------------------------------------------
      
      sklearn/tree/_splitter.pyx:1033:14: Cannot assign type 'int (const void *, const void *) except? -1 nogil' to 'int (*)(const void *, const void *) noexcept nogil'. Exception values are incompatible. Suggest adding 'noexcept' to type 'int (const void *, const void *) except? -1 nogil'.
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/tree/_splitter.pyx
      warning: sklearn/tree/_tree.pxd:61:73: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:62:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:63:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:84:72: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:89:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:57:45: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:58:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:59:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:60:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pyx:267:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pyx:414:76: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pyx:668:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pyx:680:70: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pyx:714:73: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:49:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:87:61: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:119:56: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:137:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:139:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:160:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:161:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:72:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:91:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:94:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:95:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:96:80: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
      
              # Initial capacity
              cdef int init_capacity
      
              if tree.max_depth <= 10:
                  init_capacity = (2 ** (tree.max_depth + 1)) - 1
                                                              ^
      ------------------------------------------------------------
      
      sklearn/tree/_tree.pyx:146:56: Cannot assign type 'double' to 'int'
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/tree/_tree.pyx
      warning: sklearn/tree/_utils.pxd:49:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:87:61: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:119:56: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:137:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:139:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:160:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pxd:161:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:61:73: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:62:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_tree.pxd:63:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:84:72: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_splitter.pxd:89:68: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:57:45: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:58:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:59:48: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_criterion.pxd:60:57: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:72:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:91:51: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:94:59: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:95:63: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/neighbors/_quad_tree.pxd:96:80: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pyx:25:75: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pyx:110:61: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pyx:226:56: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pyx:314:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pyx:331:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pyx:489:40: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      warning: sklearn/tree/_utils.pyx:503:71: The keyword 'nogil' should appear at the end of the function signature line. Placing it before 'except' or 'noexcept' will be disallowed in a future version of Cython.
      
      Error compiling Cython file:
      ------------------------------------------------------------
      ...
              dec(end)
              # Construct our arguments
              cdef pair[ITYPE_t, DTYPE_t] args
              args.first = key
              args.second = value
              self.my_map.insert(end, args)
                                 ^
      ------------------------------------------------------------
      
      sklearn/utils/_fast_dict.pyx:136:27: Cannot assign type 'iterator' to 'const_iterator'
      Traceback (most recent call last):
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/utils/_fast_dict.pyx
      warning: sklearn/utils/_openmp_helpers.pyx:1:0: The 'IF' statement is deprecated and will be removed in a future Cython version. Consider using runtime conditions or C macros instead. See https://github.com/cython/cython/issues/4310
      warning: sklearn/utils/_openmp_helpers.pyx:44:4: The 'IF' statement is deprecated and will be removed in a future Cython version. Consider using runtime conditions or C macros instead. See https://github.com/cython/cython/issues/4310
      multiprocessing.pool.RemoteTraceback:
      """
      Traceback (most recent call last):
        File "/usr/lib/python3.11/multiprocessing/pool.py", line 125, in worker
          result = (True, func(*args, **kwds))
                          ^^^^^^^^^^^^^^^^^^^
        File "/usr/lib/python3.11/multiprocessing/pool.py", line 48, in mapstar
          return list(map(*args))
                 ^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1325, in cythonize_one_helper
          return cythonize_one(*m)
                 ^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1301, in cythonize_one
          raise CompileError(None, pyx_file)
      Cython.Compiler.Errors.CompileError: sklearn/ensemble/_hist_gradient_boosting/splitting.pyx
      """
      [ 1/55] Cythonizing sklearn/__check_build/_check_build.pyx
      [ 2/55] Cythonizing sklearn/_isotonic.pyx
      [ 3/55] Cythonizing sklearn/cluster/_dbscan_inner.pyx
      [ 4/55] Cythonizing sklearn/cluster/_hierarchical_fast.pyx
      [ 5/55] Cythonizing sklearn/cluster/_k_means_common.pyx
      [ 6/55] Cythonizing sklearn/cluster/_k_means_elkan.pyx
      [ 7/55] Cythonizing sklearn/cluster/_k_means_lloyd.pyx
      [ 8/55] Cythonizing sklearn/cluster/_k_means_minibatch.pyx
      [ 9/55] Cythonizing sklearn/datasets/_svmlight_format_fast.pyx
      [10/55] Cythonizing sklearn/decomposition/_cdnmf_fast.pyx
      [11/55] Cythonizing sklearn/decomposition/_online_lda_fast.pyx
      [12/55] Cythonizing sklearn/ensemble/_gradient_boosting.pyx
      [13/55] Cythonizing sklearn/ensemble/_hist_gradient_boosting/_binning.pyx
      [14/55] Cythonizing sklearn/ensemble/_hist_gradient_boosting/_bitset.pyx
      [15/55] Cythonizing sklearn/ensemble/_hist_gradient_boosting/_gradient_boosting.pyx
      [16/55] Cythonizing sklearn/ensemble/_hist_gradient_boosting/_loss.pyx
      [17/55] Cythonizing sklearn/ensemble/_hist_gradient_boosting/_predictor.pyx
      [18/55] Cythonizing sklearn/ensemble/_hist_gradient_boosting/common.pyx
      [19/55] Cythonizing sklearn/ensemble/_hist_gradient_boosting/histogram.pyx
      [20/55] Cythonizing sklearn/ensemble/_hist_gradient_boosting/splitting.pyx
      [21/55] Cythonizing sklearn/ensemble/_hist_gradient_boosting/utils.pyx
      [22/55] Cythonizing sklearn/feature_extraction/_hashing_fast.pyx
      [23/55] Cythonizing sklearn/linear_model/_cd_fast.pyx
      [24/55] Cythonizing sklearn/linear_model/_sag_fast.pyx
      [25/55] Cythonizing sklearn/linear_model/_sgd_fast.pyx
      [26/55] Cythonizing sklearn/manifold/_barnes_hut_tsne.pyx
      [27/55] Cythonizing sklearn/manifold/_utils.pyx
      [28/55] Cythonizing sklearn/metrics/_dist_metrics.pyx
      [29/55] Cythonizing sklearn/metrics/_pairwise_fast.pyx
      [30/55] Cythonizing sklearn/metrics/cluster/_expected_mutual_info_fast.pyx
      [31/55] Cythonizing sklearn/neighbors/_ball_tree.pyx
      [32/55] Cythonizing sklearn/neighbors/_kd_tree.pyx
      [33/55] Cythonizing sklearn/neighbors/_partition_nodes.pyx
      [34/55] Cythonizing sklearn/neighbors/_quad_tree.pyx
      [35/55] Cythonizing sklearn/preprocessing/_csr_polynomial_expansion.pyx
      [36/55] Cythonizing sklearn/svm/_liblinear.pyx
      [37/55] Cythonizing sklearn/svm/_libsvm.pyx
      [38/55] Cythonizing sklearn/svm/_libsvm_sparse.pyx
      [39/55] Cythonizing sklearn/svm/_newrand.pyx
      [40/55] Cythonizing sklearn/tree/_criterion.pyx
      [41/55] Cythonizing sklearn/tree/_splitter.pyx
      [42/55] Cythonizing sklearn/tree/_tree.pyx
      [43/55] Cythonizing sklearn/tree/_utils.pyx
      [44/55] Cythonizing sklearn/utils/_cython_blas.pyx
      [45/55] Cythonizing sklearn/utils/_fast_dict.pyx
      [46/55] Cythonizing sklearn/utils/_logistic_sigmoid.pyx
      [47/55] Cythonizing sklearn/utils/_openmp_helpers.pyx
      [48/55] Cythonizing sklearn/utils/_random.pyx
      [49/55] Cythonizing sklearn/utils/_readonly_array_wrapper.pyx
      [50/55] Cythonizing sklearn/utils/_seq_dataset.pyx
      [51/55] Cythonizing sklearn/utils/_typedefs.pyx
      [52/55] Cythonizing sklearn/utils/_weight_vector.pyx
      [53/55] Cythonizing sklearn/utils/arrayfuncs.pyx
      [54/55] Cythonizing sklearn/utils/murmurhash.pyx
      [55/55] Cythonizing sklearn/utils/sparsefuncs_fast.pyx
      
      The above exception was the direct cause of the following exception:
      
      Traceback (most recent call last):
        File "/home/developer/ml/lib/python3.11/site-packages/pip/_vendor/pyproject_hooks/_in_process/_in_process.py", line 353, in <module>
          main()
        File "/home/developer/ml/lib/python3.11/site-packages/pip/_vendor/pyproject_hooks/_in_process/_in_process.py", line 335, in main
          json_out['return_val'] = hook(**hook_input['kwargs'])
                                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        File "/home/developer/ml/lib/python3.11/site-packages/pip/_vendor/pyproject_hooks/_in_process/_in_process.py", line 149, in prepare_metadata_for_build_wheel
          return hook(metadata_directory, config_settings)
                 ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/setuptools/build_meta.py", line 174, in prepare_metadata_for_build_wheel
          self.run_setup()
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/setuptools/build_meta.py", line 268, in run_setup
          self).run_setup(setup_script=setup_script)
                ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/setuptools/build_meta.py", line 158, in run_setup
          exec(compile(code, __file__, 'exec'), locals())
        File "setup.py", line 319, in <module>
          setup_package()
        File "setup.py", line 315, in setup_package
          setup(**metadata)
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/numpy/distutils/core.py", line 135, in setup
          config = configuration()
                   ^^^^^^^^^^^^^^^
        File "setup.py", line 201, in configuration
          config.add_subpackage("sklearn")
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/numpy/distutils/misc_util.py", line 1050, in add_subpackage
          config_list = self.get_subpackage(subpackage_name, subpackage_path,
                        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/numpy/distutils/misc_util.py", line 1016, in get_subpackage
          config = self._get_configuration_from_setup_py(
                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/numpy/distutils/misc_util.py", line 958, in _get_configuration_from_setup_py
          config = setup_module.configuration(*args)
                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        File "/tmp/pip-install-ri_bzw4u/scikit-learn_4e5a066756644a64a10b80961b0553f5/sklearn/setup.py", line 85, in configuration
          cythonize_extensions(top_path, config)
        File "/tmp/pip-install-ri_bzw4u/scikit-learn_4e5a066756644a64a10b80961b0553f5/sklearn/_build_utils/__init__.py", line 78, in cythonize_extensions
          config.ext_modules = cythonize(
                               ^^^^^^^^^^
        File "/tmp/pip-build-env-p7f1ksvr/overlay/lib/python3.11/site-packages/Cython/Build/Dependencies.py", line 1125, in cythonize
          result.get(99999)  # seconds
          ^^^^^^^^^^^^^^^^^
        File "/usr/lib/python3.11/multiprocessing/pool.py", line 774, in get
          raise self._value
      Cython.Compiler.Errors.CompileError: sklearn/ensemble/_hist_gradient_boosting/splitting.pyx
      [end of output]
  
  note: This error originates from a subprocess, and is likely not a problem with pip.
error: metadata-generation-failed

× Encountered error while generating package metadata.
╰─> See above for output.

note: This is an issue with the package mentioned above, not pip.
hint: See above for details.
