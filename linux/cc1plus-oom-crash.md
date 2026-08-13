# cc1plus oom

```
[ 1266.262000] cc1plus invoked oom-killer: gfp_mask=0x140dca(GFP_HIGHUSER_MOVABLE|__GFP_ZERO|__GFP_COMP), order=0, oom_score_adj=0
[ 1266.262015] CPU: 22 UID: 1000 PID: 9810 Comm: cc1plus Tainted: P           OE       6.18.41-1-lts #1 PREEMPT(voluntary)  b1982eced5ac3ccfd79f136103dd771797e316de
[ 1266.262020] Tainted: [P]=PROPRIETARY_MODULE, [O]=OOT_MODULE, [E]=UNSIGNED_MODULE
[ 1266.262022] Hardware name: HUANANZHI /X79M-PRO
[ 1266.262024] Call Trace:
[ 1266.262027]  <TASK>
[ 1266.262030]  dump_stack_lvl+0x5d/0x80
[ 1266.262038]  dump_header+0x43/0x1aa
[ 1266.262044]  oom_kill_process.cold+0x8/0x8a
[ 1266.262048]  out_of_memory+0x231/0x570
[ 1266.262053]  __alloc_pages_slowpath.constprop.0+0xc96/0xe60
[ 1266.262060]  __alloc_frozen_pages_noprof+0x342/0x380
[ 1266.262064]  alloc_pages_mpol+0x86/0x170
[ 1266.262070]  vma_alloc_folio_noprof+0x6a/0xd0
[ 1266.262074]  do_anonymous_page+0x362/0x9d0
[ 1266.262078]  ? ___pte_offset_map+0x1b/0x160
[ 1266.262082]  __handle_mm_fault+0xa5c/0xf10
[ 1266.262085]  ? asm_sysvec_call_function+0x1a/0x20
[ 1266.262090]  handle_mm_fault+0xe7/0x2b0
[ 1266.262094]  do_user_addr_fault+0x23b/0x730
[ 1266.262099]  exc_page_fault+0x8f/0x1d0
[ 1266.262104]  asm_exc_page_fault+0x26/0x30
[ 1266.262107] RIP: 0033:0x7f448e6b367b
[ 1266.262111] Code: 4c 16 fc 8b 36 89 4c 17 fc 89 37 c3 0f 1f 00 48 8b 4c 16 f8 48 8b 36 48 89 37 48 89 4c 17 f8 c3 0f 10 54 16 f0 0f 10 5c 16 e0 <0f> 11 07 0f 11 4f 10 0f 11 54 17 f0 0f 11 5c 17 e0 c3 0f 1f 00 48
[ 1266.262113] RSP: 002b:00007ffe6023ce18 EFLAGS: 00010246
[ 1266.262116] RAX: 00007f446dc1e000 RBX: 00007f446dc1e000 RCX: 0000000000000001
[ 1266.262118] RDX: 0000000000000040 RSI: 00007f446dffbfc0 RDI: 00007f446dc1e000
[ 1266.262120] RBP: 00007f446dffbfc0 R08: 000000000000000c R09: 000000001b9e17a0
[ 1266.262121] R10: 0000000000000050 R11: 000000001ccf4370 R12: 0000000000000045
[ 1266.262123] R13: 0000000000000040 R14: 0000000000000000 R15: 0000000000000002
[ 1266.262126]  </TASK>
[ 1266.262128] Mem-Info:
[ 1266.262136] active_anon:470546 inactive_anon:3378958 isolated_anon:0
                active_file:1255 inactive_file:2066 isolated_file:0
                unevictable:0 dirty:0 writeback:0
                slab_reclaimable:39748 slab_unreclaimable:41974
                mapped:25372 shmem:159453 pagetables:14248
                sec_pagetables:257 bounce:0
                kernel_misc_reclaimable:0
                free:45191 free_pcp:941 free_cma:0
[ 1266.262143] Node 0 active_anon:1882184kB inactive_anon:13515832kB active_file:5344kB inactive_file:8264kB unevictable:0kB isolated(anon):0kB isolated(file):0kB mapped:101488kB dirty:0kB writeback:0kB shmem:637812kB shmem_thp:0kB shmem_pmdmapped:0kB anon_thp:9394176kB kernel_stack:13248kB pagetables:56992kB sec_pagetables:1028kB all_unreclaimable? no Balloon:0kB
[ 1266.262149] Node 0 DMA free:13312kB boost:0kB min:64kB low:80kB high:96kB reserved_highatomic:0KB free_highatomic:0KB active_anon:0kB inactive_anon:0kB active_file:0kB inactive_file:0kB unevictable:0kB writepending:0kB zspages:0kB present:15996kB managed:15360kB mlocked:0kB bounce:0kB free_pcp:0kB local_pcp:0kB free_cma:0kB
[ 1266.262156] lowmem_reserve[]: 0 1900 15897 15897 15897
[ 1266.262163] Node 0 DMA32 free:63088kB boost:0kB min:7004kB low:8752kB high:10500kB reserved_highatomic:0KB free_highatomic:0KB active_anon:218696kB inactive_anon:1659408kB active_file:96kB inactive_file:952kB unevictable:0kB writepending:0kB zspages:0kB present:2015196kB managed:1946288kB mlocked:0kB bounce:0kB free_pcp:2004kB local_pcp:0kB free_cma:0kB
[ 1266.262170] lowmem_reserve[]: 0 0 13997 13997 13997
[ 1266.262176] Node 0 Normal free:105428kB boost:61440kB min:121952kB low:137080kB high:152208kB reserved_highatomic:0KB free_highatomic:0KB active_anon:1663488kB inactive_anon:11856424kB active_file:5100kB inactive_file:7352kB unevictable:0kB writepending:0kB zspages:0kB present:14680064kB managed:14333152kB mlocked:0kB bounce:0kB free_pcp:512kB local_pcp:0kB free_cma:0kB
[ 1266.262183] lowmem_reserve[]: 0 0 0 0 0
[ 1266.262188] Node 0 DMA: 0*4kB 0*8kB 0*16kB 0*32kB 0*64kB 0*128kB 0*256kB 0*512kB 1*1024kB (U) 2*2048kB (UM) 2*4096kB (M) = 13312kB
[ 1266.262206] Node 0 DMA32: 10*4kB (UM) 102*8kB (UM) 16*16kB (UM) 36*32kB (UM) 37*64kB (UM) 20*128kB (UM) 8*256kB (UM) 6*512kB (UM) 4*1024kB (M) 3*2048kB (UM) 10*4096kB (ME) = 63512kB
[ 1266.262230] Node 0 Normal: 1404*4kB (UME) 521*8kB (UME) 1149*16kB (UME) 902*32kB (UME) 401*64kB (UE) 131*128kB (UE) 26*256kB (UE) 0*512kB 0*1024kB 0*2048kB 0*4096kB = 106120kB
[ 1266.262253] Node 0 hugepages_total=0 hugepages_free=0 hugepages_surp=0 hugepages_size=1048576kB
[ 1266.262256] Node 0 hugepages_total=0 hugepages_free=0 hugepages_surp=0 hugepages_size=2048kB
[ 1266.262258] 163699 total pagecache pages
[ 1266.262259] 0 pages in swap cache
[ 1266.262261] Free swap  = 0kB
[ 1266.262262] Total swap = 0kB
[ 1266.262263] 4177814 pages RAM
[ 1266.262265] 0 pages HighMem/MovableOnly
[ 1266.262266] 104114 pages reserved
[ 1266.262267] 0 pages cma reserved
[ 1266.262268] 0 pages hwpoisoned
[ 1266.262269] Tasks state (memory values in pages):
[ 1266.262270] [  pid  ]   uid  tgid total_vm      rss rss_anon rss_file rss_shmem pgtables_bytes swapents oom_score_adj name
[ 1266.262690] [  10046]  1000 10046     2015      196       48      148         0    53248        0             0 deps.sh
[ 1266.262695] [  10219]  1000 10219     5605      987      624      363         0    77824        0             0 st
[ 1266.262698] [  10220]  1000 10220    56024      688      528      160         0   114688        0             0 fish
[ 1266.262702] [  10453]  1000 10453   108641     4667     4187      480         0   176128        0             0 btop
[ 1266.262706] [   9284]  1000  9284     3321     1493     1276      217         0    73728        0             0 ninja
[ 1266.262710] [   9322]  1000  9322     1965      218       48      170         0    49152        0             0 g++
[ 1266.262714] [   9326]  1000  9326     1965      218       48      170         0    49152        0             0 g++
[ 1266.262717] [   9343]  1000  9343   334588   308340   307629      711         0  2662400        0             0 cc1plus
[ 1266.262720] [   9345]  1000  9345   397365   369596   368639      957         0  3174400        0             0 cc1plus
[ 1266.262724] [   9514]  1000  9514     1965      218       48      170         0    57344        0             0 g++
[ 1266.262728] [   9515]  1000  9515   240564   223133   222682      451         0  1986560        0             0 cc1plus
[ 1266.262731] [   9582]  1000  9582     1965      249       48      201         0    45056        0             0 g++
[ 1266.262734] [   9583]  1000  9583   205076   188312   187772      540         0  1683456        0             0 cc1plus
[ 1266.262737] [   9677]  1000  9677     1964      218       48      170         0    49152        0             0 g++
[ 1266.262741] [   9678]  1000  9678   214254   188775   187905      870         0  1691648        0             0 cc1plus
[ 1266.262744] [   9710]  1000  9710     1964      218       48      170         0    49152        0             0 g++
[ 1266.262747] [   9711]  1000  9711   172500   154886   154159      727         0  1409024        0             0 cc1plus
[ 1266.262751] [   9742]  1000  9742     1965      218       48      170         0    49152        0             0 g++
[ 1266.262754] [   9743]  1000  9743   206063   180180   179481      699         0  1609728        0             0 cc1plus
[ 1266.262758] [   9766]  1000  9766     1964      218       48      170         0    49152        0             0 g++
[ 1266.262761] [   9767]  1000  9767   200660   177439   176280     1159         0  1581056        0             0 cc1plus
[ 1266.262764] [   9775]  1000  9775     1965      218       48      170         0    45056        0             0 g++
[ 1266.262768] [   9776]  1000  9776   173491   155830   154907      923         0  1396736        0             0 cc1plus
[ 1266.262771] [   9794]  1000  9794     1965      266       48      218         0    45056        0             0 g++
[ 1266.262774] [   9795]  1000  9795   152456   135053   134253      800         0  1245184        0             0 cc1plus
[ 1266.262778] [   9798]  1000  9798     1964      218       48      170         0    53248        0             0 g++
[ 1266.262781] [   9799]  1000  9799   151579   134362   133141     1221         0  1220608        0             0 cc1plus
[ 1266.262785] [   9809]  1000  9809     1964      218       48      170         0    49152        0             0 g++
[ 1266.262788] [   9810]  1000  9810   152650   136014   133956     2058         0  1241088        0             0 cc1plus
[ 1266.262791] [   9816]  1000  9816     1964      218       48      170         0    53248        0             0 g++
[ 1266.262794] [   9817]  1000  9817   146327   131475   129488     1987         0  1191936        0             0 cc1plus
[ 1266.262798] [   9825]  1000  9825     1964      218       48      170         0    53248        0             0 g++
[ 1266.262801] [   9826]  1000  9826   145475   128956   126878     2078         0  1167360        0             0 cc1plus
[ 1266.262805] [   9840]  1000  9840     1965      218       48      170         0    45056        0             0 g++
[ 1266.262808] [   9841]  1000  9841   144778   121112   118823     2289         0  1110016        0             0 cc1plus
[ 1266.262811] [   9852]  1000  9852     1965      218       48      170         0    45056        0             0 g++
[ 1266.262814] [   9853]  1000  9853   140505   124051   121814     2237         0  1126400        0             0 cc1plus
[ 1266.262818] [   9854]  1000  9854     1965      218       48      170         0    49152        0             0 g++
[ 1266.262822] [   9855]  1000  9855   132371   115976   113635     2341         0  1069056        0             0 cc1plus
[ 1266.262825] [   9865]  1000  9865     1964      218       48      170         0    53248        0             0 g++
[ 1266.262829] [   9866]  1000  9866   148255   124015   122101     1914         0  1130496        0             0 cc1plus
[ 1266.262832] [   9890]  1000  9890     1965      218       48      170         0    45056        0             0 g++
[ 1266.262836] [   9891]  1000  9891    94036    78704    76584     2120         0   770048        0             0 cc1plus
[ 1266.262840] [   9902]  1000  9902     1965      266       48      218         0    53248        0             0 g++
[ 1266.262843] [   9903]  1000  9903    92299    74389    72912     1477         0   737280        0             0 cc1plus
[ 1266.262846] [   9920]  1000  9920     1964      249       48      201         0    49152        0             0 g++
[ 1266.262850] [   9921]  1000  9921    77749    61614    59770     1844         0   630784        0             0 cc1plus
[ 1266.262853] [   9935]  1000  9935     1964      218       48      170         0    53248        0             0 g++
[ 1266.262856] [   9936]  1000  9936    73891    58865    56721     2144         0   606208        0             0 cc1plus
[ 1266.262859] [   9944]  1000  9944     1965      218       48      170         0    49152        0             0 g++
[ 1266.262862] [   9945]  1000  9945    57993    41616    39907     1709         0   471040        0             0 cc1plus
[ 1266.262866] [   9952]  1000  9952     1965      218       48      170         0    49152        0             0 g++
[ 1266.262870] [   9953]  1000  9953    55768    38369    36571     1798         0   434176        0             0 cc1plus
[ 1266.262873] [   9964]  1000  9964     1965      218       48      170         0    49152        0             0 g++
[ 1266.262877] [   9965]  1000  9965    45843    28012    25904     2108         0   356352        0             0 cc1plus
[ 1266.262880] [   9971]  1000  9971     1965      323       48      275         0    49152        0             0 g++
[ 1266.262883] [   9972]  1000  9972    27461    11389    10321     1068         0   221184        0             0 cc1plus
[ 1266.262887] oom-kill:constraint=CONSTRAINT_NONE,nodemask=(null),cpuset=1,mems_allowed=0,global_oom,task_memcg=/1,task=brave,pid=4511,uid=1000
[ 1266.262964] Out of memory: Killed process 4511 (brave) total-vm:55045028kB, anon-rss:24580kB, file-rss:2576kB, shmem-rss:0kB, UID:1000 pgtables:884kB oom_score_adj:200
[ 1271.227386] importas invoked oom-killer: gfp_mask=0x140cca(GFP_HIGHUSER_MOVABLE|__GFP_COMP), order=0, oom_score_adj=0
[ 1271.227400] CPU: 17 UID: 0 PID: 9980 Comm: importas Tainted: P           OE       6.18.41-1-lts #1 PREEMPT(voluntary)  b1982eced5ac3ccfd79f136103dd771797e316de
[ 1271.227404] Tainted: [P]=PROPRIETARY_MODULE, [O]=OOT_MODULE, [E]=UNSIGNED_MODULE
[ 1271.227405] Hardware name: HUANANZHI /X79M-PRO
[ 1271.227407] Call Trace:
[ 1271.227409]  <TASK>
[ 1271.227411]  dump_stack_lvl+0x5d/0x80
[ 1271.227418]  dump_header+0x43/0x1aa
[ 1271.227423]  oom_kill_process.cold+0x8/0x8a
[ 1271.227426]  out_of_memory+0x231/0x570
[ 1271.227430]  __alloc_pages_slowpath.constprop.0+0xc96/0xe60
[ 1271.227435]  __alloc_frozen_pages_noprof+0x342/0x380
[ 1271.227437]  alloc_pages_mpol+0x86/0x170
[ 1271.227442]  ? down_write+0x12/0x60
[ 1271.227447]  vma_alloc_folio_noprof+0x6a/0xd0
[ 1271.227449]  do_fault+0x8b/0x600
[ 1271.227452]  ? ___pte_offset_map+0x1b/0x160
[ 1271.227454]  __handle_mm_fault+0x941/0xf10
[ 1271.227458]  handle_mm_fault+0xe7/0x2b0
[ 1271.227460]  do_user_addr_fault+0x23b/0x730
[ 1271.227464]  exc_page_fault+0x8f/0x1d0
[ 1271.227467]  asm_exc_page_fault+0x26/0x30
[ 1271.227469] RIP: 0033:0x7f8a230128ca
[ 1271.227473] Code: 0f 61 c0 66 0f 70 c0 00 48 83 fa 10 0f 82 7e 00 00 00 48 83 fa 20 77 12 0f 11 44 17 f0 0f 11 07 c3 0f 11 47 e0 0f 11 47 f0 c3 <0f> 11 07 0f 11 47 10 48 01 d7 48 83 fa 40 76 e7 0f 11 40 20 0f 11
[ 1271.227475] RSP: 002b:00007ffcadc89598 EFLAGS: 00010206
[ 1271.227477] RAX: 00007f8a22fb7684 RBX: 00007f8a22fb8000 RCX: 00007f8a23011816
[ 1271.227479] RDX: 000000000000097c RSI: 0000000000000000 RDI: 00007f8a22fb7684
[ 1271.227481] RBP: 00007f8a22fcb5a0 R08: 0000000000000003 R09: 0000000000043000
[ 1271.227482] R10: 0000000000000400 R11: 0000000000000206 R12: 00007f8a22fc0b20
[ 1271.227483] R13: 00007f8a22fb8000 R14: 00007f8a22fb7684 R15: 00000000000002a8
[ 1271.227486]  </TASK>
[ 1271.227504] Mem-Info:
[ 1271.227507] active_anon:3385355 inactive_anon:464440 isolated_anon:0
                active_file:0 inactive_file:2831 isolated_file:0
                unevictable:0 dirty:2 writeback:0
                slab_reclaimable:39712 slab_unreclaimable:42020
                mapped:23280 shmem:159364 pagetables:14076
                sec_pagetables:257 bounce:0
                kernel_misc_reclaimable:0
                free:46587 free_pcp:642 free_cma:0
[ 1271.227511] Node 0 active_anon:13541420kB inactive_anon:1857760kB active_file:0kB inactive_file:11324kB unevictable:0kB isolated(anon):0kB isolated(file):0kB mapped:93120kB dirty:8kB writeback:0kB shmem:637456kB shmem_thp:0kB shmem_pmdmapped:0kB anon_thp:9396224kB kernel_stack:13024kB pagetables:56304kB sec_pagetables:1028kB all_unreclaimable? no Balloon:0kB
[ 1271.227515] Node 0 DMA free:13312kB boost:0kB min:64kB low:80kB high:96kB reserved_highatomic:0KB free_highatomic:0KB active_anon:0kB inactive_anon:0kB active_file:0kB inactive_file:0kB unevictable:0kB writepending:0kB zspages:0kB present:15996kB managed:15360kB mlocked:0kB bounce:0kB free_pcp:0kB local_pcp:0kB free_cma:0kB
[ 1271.227520] lowmem_reserve[]: 0 1900 15897 15897 15897
[ 1271.227524] Node 0 DMA32 free:62964kB boost:0kB min:7004kB low:8752kB high:10500kB reserved_highatomic:0KB free_highatomic:0KB active_anon:1659896kB inactive_anon:219152kB active_file:0kB inactive_file:564kB unevictable:0kB writepending:0kB zspages:0kB present:2015196kB managed:1946288kB mlocked:0kB bounce:0kB free_pcp:1860kB local_pcp:0kB free_cma:0kB
[ 1271.227529] lowmem_reserve[]: 0 0 13997 13997 13997
[ 1271.227532] Node 0 Normal free:110376kB boost:55296kB min:115808kB low:130936kB high:146064kB reserved_highatomic:0KB free_highatomic:0KB active_anon:11881524kB inactive_anon:1638608kB active_file:0kB inactive_file:10876kB unevictable:0kB writepending:8kB zspages:0kB present:14680064kB managed:14333152kB mlocked:0kB bounce:0kB free_pcp:72kB local_pcp:0kB free_cma:0kB
[ 1271.227536] lowmem_reserve[]: 0 0 0 0 0
[ 1271.227540] Node 0 DMA: 0*4kB 0*8kB 0*16kB 0*32kB 0*64kB 0*128kB 0*256kB 0*512kB 1*1024kB (U) 2*2048kB (UM) 2*4096kB (M) = 13312kB
[ 1271.227550] Node 0 DMA32: 46*4kB (UM) 71*8kB (UM) 20*16kB (UM) 32*32kB (UM) 31*64kB (UM) 20*128kB (UM) 8*256kB (UM) 6*512kB (UM) 4*1024kB (M) 3*2048kB (UM) 10*4096kB (ME) = 62960kB
[ 1271.227566] Node 0 Normal: 1822*4kB (UME) 672*8kB (UME) 1193*16kB (UME) 911*32kB (UME) 403*64kB (UME) 132*128kB (UE) 26*256kB (UE) 0*512kB 0*1024kB 0*2048kB 0*4096kB = 110248kB
[ 1271.227578] Node 0 hugepages_total=0 hugepages_free=0 hugepages_surp=0 hugepages_size=1048576kB
[ 1271.227580] Node 0 hugepages_total=0 hugepages_free=0 hugepages_surp=0 hugepages_size=2048kB
[ 1271.227581] 162347 total pagecache pages
[ 1271.227582] 0 pages in swap cache
[ 1271.227583] Free swap  = 0kB
[ 1271.227583] Total swap = 0kB
[ 1271.227584] 4177814 pages RAM
[ 1271.227585] 0 pages HighMem/MovableOnly
[ 1271.227586] 104114 pages reserved
[ 1271.227586] 0 pages cma reserved
[ 1271.227587] 0 pages hwpoisoned
[ 1271.227587] Tasks state (memory values in pages):
[ 1271.227588] [  pid  ]   uid  tgid total_vm      rss rss_anon rss_file rss_shmem pgtables_bytes swapents oom_score_adj name
[ 1271.227835] [  10046]  1000 10046     2015      196       48      148         0    53248        0             0 deps.sh
[ 1271.227838] [  10219]  1000 10219     5605      987      624      363         0    77824        0             0 st
[ 1271.227839] [  10220]  1000 10220    56024      688      528      160         0   114688        0             0 fish
[ 1271.227842] [  10453]  1000 10453   108641     4595     4187      408         0   180224        0             0 btop
[ 1271.227845] [   9284]  1000  9284     3321     1493     1276      217         0    73728        0             0 ninja
[ 1271.227847] [   9322]  1000  9322     1965      218       48      170         0    49152        0             0 g++
[ 1271.227849] [   9326]  1000  9326     1965      218       48      170         0    49152        0             0 g++
[ 1271.227851] [   9343]  1000  9343   334588   308005   307629      376         0  2662400        0             0 cc1plus
[ 1271.227853] [   9345]  1000  9345   397365   369178   368687      491         0  3174400        0             0 cc1plus
[ 1271.227855] [   9514]  1000  9514     1965      218       48      170         0    57344        0             0 g++
[ 1271.227857] [   9515]  1000  9515   240564   223112   222682      430         0  1986560        0             0 cc1plus
[ 1271.227859] [   9582]  1000  9582     1965      249       48      201         0    45056        0             0 g++
[ 1271.227861] [   9583]  1000  9583   205076   188217   187772      445         0  1687552        0             0 cc1plus
[ 1271.227863] [   9677]  1000  9677     1964      218       48      170         0    49152        0             0 g++
[ 1271.227865] [   9678]  1000  9678   214254   188341   187905      436         0  1691648        0             0 cc1plus
[ 1271.227867] [   9710]  1000  9710     1964      218       48      170         0    49152        0             0 g++
[ 1271.227869] [   9711]  1000  9711   172500   154559   154159      400         0  1409024        0             0 cc1plus
[ 1271.227872] [   9742]  1000  9742     1965      218       48      170         0    49152        0             0 g++
[ 1271.227874] [   9743]  1000  9743   206063   179843   179481      362         0  1609728        0             0 cc1plus
[ 1271.227876] [   9766]  1000  9766     1964      218       48      170         0    49152        0             0 g++
[ 1271.227877] [   9767]  1000  9767   200660   176945   176280      665         0  1581056        0             0 cc1plus
[ 1271.227880] [   9775]  1000  9775     1965      218       48      170         0    45056        0             0 g++
[ 1271.227882] [   9776]  1000  9776   173491   155725   154955      770         0  1396736        0             0 cc1plus
[ 1271.227883] [   9794]  1000  9794     1965      266       48      218         0    45056        0             0 g++
[ 1271.227885] [   9795]  1000  9795   152456   134703   134253      450         0  1245184        0             0 cc1plus
[ 1271.227887] [   9798]  1000  9798     1964      218       48      170         0    53248        0             0 g++
[ 1271.227889] [   9799]  1000  9799   152091   134151   133333      818         0  1224704        0             0 cc1plus
[ 1271.227891] [   9809]  1000  9809     1964      218       48      170         0    49152        0             0 g++
[ 1271.227893] [   9810]  1000  9810   152683   134858   134196      662         0  1241088        0             0 cc1plus
[ 1271.227895] [   9816]  1000  9816     1964      218       48      170         0    53248        0             0 g++
[ 1271.227896] [   9817]  1000  9817   146839   130470   129680      790         0  1196032        0             0 cc1plus
[ 1271.227898] [   9825]  1000  9825     1964      218       48      170         0    53248        0             0 g++
[ 1271.227900] [   9826]  1000  9826   145987   127915   127166      749         0  1171456        0             0 cc1plus
[ 1271.227902] [   9840]  1000  9840     1965      218       48      170         0    45056        0             0 g++
[ 1271.227904] [   9841]  1000  9841   144778   119623   118823      800         0  1114112        0             0 cc1plus
[ 1271.227906] [   9852]  1000  9852     1965      218       48      170         0    45056        0             0 g++
[ 1271.227908] [   9853]  1000  9853   141017   123322   122342      980         0  1130496        0             0 cc1plus
[ 1271.227910] [   9854]  1000  9854     1965      218       48      170         0    49152        0             0 g++
[ 1271.227912] [   9855]  1000  9855   132917   114823   113971      852         0  1073152        0             0 cc1plus
[ 1271.227914] [   9865]  1000  9865     1964      218       48      170         0    53248        0             0 g++
[ 1271.227915] [   9866]  1000  9866   149312   123921   123098      823         0  1138688        0             0 cc1plus
[ 1271.227917] [   9890]  1000  9890     1965      218       48      170         0    45056        0             0 g++
[ 1271.227919] [   9891]  1000  9891    94548    77821    77016      805         0   774144        0             0 cc1plus
[ 1271.227921] [   9902]  1000  9902     1965      266       48      218         0    53248        0             0 g++
[ 1271.227923] [   9903]  1000  9903    92299    73660    72912      748         0   741376        0             0 cc1plus
[ 1271.227925] [   9920]  1000  9920     1964      249       48      201         0    49152        0             0 g++
[ 1271.227927] [   9921]  1000  9921    78261    60829    60106      723         0   634880        0             0 cc1plus
[ 1271.227928] [   9935]  1000  9935     1964      218       48      170         0    53248        0             0 g++
[ 1271.227931] [   9936]  1000  9936    74407    58223    57345      878         0   618496        0             0 cc1plus
[ 1271.227933] [   9944]  1000  9944     1965      218       48      170         0    49152        0             0 g++
[ 1271.227935] [   9945]  1000  9945    58582    41021    40195      826         0   475136        0             0 cc1plus
[ 1271.227937] [   9952]  1000  9952     1965      218       48      170         0    49152        0             0 g++
[ 1271.227939] [   9953]  1000  9953    56856    38126    37344      782         0   446464        0             0 cc1plus
[ 1271.227941] [   9964]  1000  9964     1965      218       48      170         0    49152        0             0 g++
[ 1271.227943] [   9965]  1000  9965    46363    27240    26384      856         0   360448        0             0 cc1plus
[ 1271.227945] [   9971]  1000  9971     1965      323       48      275         0    49152        0             0 g++
[ 1271.227948] [   9972]  1000  9972    27461    11188    10513      675         0   225280        0             0 cc1plus
[ 1271.227950] [   9980]     0  9980      224      101        0      101         0    36864        0             0 importas
[ 1271.227953] oom-kill:constraint=CONSTRAINT_NONE,nodemask=(null),cpuset=/,mems_allowed=0,global_oom,task_memcg=/1,task=cc1plus,pid=9345,uid=1000
[ 1271.227973] Out of memory: Killed process 9345 (cc1plus) total-vm:1589460kB, anon-rss:1474748kB, file-rss:1964kB, shmem-rss:0kB, UID:1000 pgtables:3100kB oom_score_adj:0
[ 1618.921177] perf: interrupt took too long (2529 > 2500), lowering kernel.perf_event_max_sample_rate to 78900
[ 1658.609692] cc1plus invoked oom-killer: gfp_mask=0x140cca(GFP_HIGHUSER_MOVABLE|__GFP_COMP), order=0, oom_score_adj=0
[ 1658.609705] CPU: 8 UID: 1000 PID: 23590 Comm: cc1plus Tainted: P           OE       6.18.41-1-lts #1 PREEMPT(voluntary)  b1982eced5ac3ccfd79f136103dd771797e316de
[ 1658.609710] Tainted: [P]=PROPRIETARY_MODULE, [O]=OOT_MODULE, [E]=UNSIGNED_MODULE
[ 1658.609711] Hardware name: HUANANZHI /X79M-PRO
[ 1658.609712] Call Trace:
[ 1658.609714]  <TASK>
[ 1658.609716]  dump_stack_lvl+0x5d/0x80
[ 1658.609723]  dump_header+0x43/0x1aa
[ 1658.609729]  oom_kill_process.cold+0x8/0x8a
[ 1658.609733]  out_of_memory+0x231/0x570
[ 1658.609738]  __alloc_pages_slowpath.constprop.0+0xc96/0xe60
[ 1658.609743]  __alloc_frozen_pages_noprof+0x342/0x380
[ 1658.609746]  alloc_pages_mpol+0x86/0x170
[ 1658.609750]  folio_alloc_noprof+0x54/0xa0
[ 1658.609753]  __filemap_get_folio+0x1eb/0x5a0
[ 1658.609756]  ? page_cache_ra_unbounded+0x190/0x250
[ 1658.609760]  filemap_fault+0x30b/0x17d0
[ 1658.609762]  ? filemap_map_pages+0x535/0x7e0
[ 1658.609765]  ? folio_add_file_rmap_ptes+0x4a/0x1e0
[ 1658.609769]  ? sysvec_call_function+0xe/0x90
[ 1658.609773]  __do_fault+0x34/0x1c0
[ 1658.609777]  do_fault+0x398/0x600
[ 1658.609780]  __handle_mm_fault+0x941/0xf10
[ 1658.609783]  ? mt_find+0xfb/0x590
[ 1658.609786]  handle_mm_fault+0xe7/0x2b0
[ 1658.609788]  do_user_addr_fault+0x23b/0x730
[ 1658.609792]  exc_page_fault+0x8f/0x1d0
[ 1658.609795]  asm_exc_page_fault+0x26/0x30
[ 1658.609797] RIP: 0033:0xf46743
[ 1658.609800] Code: 0d 82 3b 6c 02 44 8b 44 24 18 66 90 66 66 2e 0f 1f 84 00 00 00 00 00 49 63 4e 0c 45 0f b7 56 08 48 8d 04 c9 4c 89 d6 48 89 cf <47> 0f b7 94 12 80 15 91 02 48 c1 e0 04 4c 01 c8 4c 63 58 78 66 47
[ 1658.609801] RSP: 002b:00007fffc716e0d0 EFLAGS: 00010202
[ 1658.609803] RAX: 0000000000000099 RBX: 0000000000000006 RCX: 0000000000000011
[ 1658.609805] RDX: 00007f73be9861c0 RSI: 000000000000000c RDI: 0000000000000011
[ 1658.609806] RBP: 0000000000000000 R08: 0000000000000382 R09: 000000001e63bdd0
[ 1658.609807] R10: 000000000000000c R11: 00007f73be98eb20 R12: 00007f73be931038
[ 1658.609807] R13: 000000000353f9e0 R14: 000000001cebcce0 R15: 00007f73be98b570
[ 1658.609809]  </TASK>
[ 1658.609812] Mem-Info:
[ 1658.609818] active_anon:1899192 inactive_anon:1945372 isolated_anon:0
                active_file:325 inactive_file:1498 isolated_file:0
                unevictable:0 dirty:0 writeback:0
                slab_reclaimable:39516 slab_unreclaimable:43275
                mapped:26021 shmem:167150 pagetables:15469
                sec_pagetables:257 bounce:0
                kernel_misc_reclaimable:0
                free:47900 free_pcp:772 free_cma:0
[ 1658.609822] Node 0 active_anon:7596768kB inactive_anon:7781488kB active_file:1300kB inactive_file:5992kB unevictable:0kB isolated(anon):0kB isolated(file):0kB mapped:104084kB dirty:0kB writeback:0kB shmem:668600kB shmem_thp:0kB shmem_pmdmapped:0kB anon_thp:9781248kB kernel_stack:14960kB pagetables:61876kB sec_pagetables:1028kB all_unreclaimable? no Balloon:0kB
[ 1658.609826] Node 0 DMA free:13312kB boost:0kB min:64kB low:80kB high:96kB reserved_highatomic:0KB free_highatomic:0KB active_anon:0kB inactive_anon:0kB active_file:0kB inactive_file:0kB unevictable:0kB writepending:0kB zspages:0kB present:15996kB managed:15360kB mlocked:0kB bounce:0kB free_pcp:0kB local_pcp:0kB free_cma:0kB
[ 1658.609831] lowmem_reserve[]: 0 1900 15897 15897 15897
[ 1658.609835] Node 0 DMA32 free:63516kB boost:0kB min:7004kB low:8752kB high:10500kB reserved_highatomic:0KB free_highatomic:0KB active_anon:996244kB inactive_anon:880028kB active_file:408kB inactive_file:1696kB unevictable:0kB writepending:0kB zspages:0kB present:2015196kB managed:1946288kB mlocked:0kB bounce:0kB free_pcp:2032kB local_pcp:0kB free_cma:0kB
[ 1658.609839] lowmem_reserve[]: 0 0 13997 13997 13997
[ 1658.609842] Node 0 Normal free:114772kB boost:55296kB min:115808kB low:130936kB high:146064kB reserved_highatomic:0KB free_highatomic:0KB active_anon:6600524kB inactive_anon:6901460kB active_file:720kB inactive_file:4412kB unevictable:0kB writepending:0kB zspages:0kB present:14680064kB managed:14333152kB mlocked:0kB bounce:0kB free_pcp:1056kB local_pcp:0kB free_cma:0kB
[ 1658.609846] lowmem_reserve[]: 0 0 0 0 0
[ 1658.609850] Node 0 DMA: 0*4kB 0*8kB 0*16kB 0*32kB 0*64kB 0*128kB 0*256kB 0*512kB 1*1024kB (U) 2*2048kB (UM) 2*4096kB (M) = 13312kB
[ 1658.609863] Node 0 DMA32: 5*4kB (UME) 6*8kB (UE) 12*16kB (UM) 16*32kB (UME) 14*64kB (UME) 15*128kB (UME) 9*256kB (ME) 10*512kB (UME) 10*1024kB (ME) 8*2048kB (UME) 6*4096kB (M) = 62212kB
[ 1658.609878] Node 0 Normal: 2278*4kB (UME) 654*8kB (UME) 358*16kB (UME) 735*32kB (UME) 597*64kB (UE) 179*128kB (UE) 29*256kB (UE) 0*512kB 0*1024kB 0*2048kB 0*4096kB = 112136kB
[ 1658.609893] Node 0 hugepages_total=0 hugepages_free=0 hugepages_surp=0 hugepages_size=1048576kB
[ 1658.609894] Node 0 hugepages_total=0 hugepages_free=0 hugepages_surp=0 hugepages_size=2048kB
[ 1658.609895] 169318 total pagecache pages
[ 1658.609896] 0 pages in swap cache
[ 1658.609897] Free swap  = 0kB
[ 1658.609898] Total swap = 0kB
[ 1658.609899] 4177814 pages RAM
[ 1658.609899] 0 pages HighMem/MovableOnly
[ 1658.609900] 104114 pages reserved
[ 1658.609901] 0 pages cma reserved
[ 1658.609901] 0 pages hwpoisoned
[ 1658.609902] Tasks state (memory values in pages):
[ 1658.609903] [  pid  ]   uid  tgid total_vm      rss rss_anon rss_file rss_shmem pgtables_bytes swapents oom_score_adj name
[ 1658.610196] [  12565]  1000 12565     2015      252       96      156         0    65536        0             0 deps.sh
[ 1658.610198] [  23534]  1000 23534     3231     1482     1276      206         0    73728        0             0 ninja
[ 1658.610202] [  23564]  1000 23564     1965      320       48      272         0    53248        0             0 g++
[ 1658.610205] [  23570]  1000 23570     1965      336       48      288         0    53248        0             0 g++
[ 1658.610207] [  23574]  1000 23574     1965      336       48      288         0    53248        0             0 g++
[ 1658.610209] [  23576]  1000 23576     1965      336       48      288         0    49152        0             0 g++
[ 1658.610211] [  23587]  1000 23587   272645   245109   244816      293         0  2146304        0             0 cc1plus
[ 1658.610213] [  23590]  1000 23590   304697   277921   277640      281         0  2424832        0             0 cc1plus
[ 1658.610215] [  23592]  1000 23592   355631   326661   326181      480         0  2809856        0             0 cc1plus
[ 1658.610217] [  23594]  1000 23594   208640   191784   191529      255         0  1716224        0             0 cc1plus
[ 1658.610219] [  23664]  1000 23664     1965      320       48      272         0    53248        0             0 g++
[ 1658.610220] [  23665]  1000 23665   214668   198188   197785      403         0  1773568        0             0 cc1plus
[ 1658.610222] [  23720]  1000 23720     1965      336       48      288         0    53248        0             0 g++
[ 1658.610224] [  23721]  1000 23721   172998   156113   155717      396         0  1425408        0             0 cc1plus
[ 1658.610226] [  23758]  1000 23758     1965      336       48      288         0    49152        0             0 g++
[ 1658.610228] [  23759]  1000 23759   210072   184509   184136      373         0  1658880        0             0 cc1plus
[ 1658.610230] [  23761]  1000 23761     1965      320       48      272         0    53248        0             0 g++
[ 1658.610232] [  23762]  1000 23762   222471   204396   204028      368         0  1810432        0             0 cc1plus
[ 1658.610234] [  23791]  1000 23791     1965      336       48      288         0    57344        0             0 g++
[ 1658.610236] [  23792]  1000 23792   171904   154464   154017      447         0  1400832        0             0 cc1plus
[ 1658.610237] [  23808]  1000 23808     1965      336       48      288         0    57344        0             0 g++
[ 1658.610239] [  23809]  1000 23809   145568   127815   127388      427         0  1187840        0             0 cc1plus
[ 1658.610241] [  23834]  1000 23834     1965      336       48      288         0    53248        0             0 g++
[ 1658.610243] [  23835]  1000 23835   164014   147290   146774      516         0  1335296        0             0 cc1plus
[ 1658.610245] [  23842]  1000 23842     1965      320       48      272         0    61440        0             0 g++
[ 1658.610247] [  23843]  1000 23843   117429    98931    98442      489         0   954368        0             0 cc1plus
[ 1658.610249] [  23879]  1000 23879     1965      336       48      288         0    53248        0             0 g++
[ 1658.610250] [  23880]  1000 23880   118446   100130    99665      465         0   966656        0             0 cc1plus
[ 1658.610252] [  23900]  1000 23900     1965      336       48      288         0    53248        0             0 g++
[ 1658.610254] [  23901]  1000 23901   118917   100178    99860      318         0   958464        0             0 cc1plus
[ 1658.610256] [  23903]  1000 23903     1965      336       48      288         0    53248        0             0 g++
[ 1658.610258] [  23904]  1000 23904   107855    88920    88499      421         0   876544        0             0 cc1plus
[ 1658.610260] [  23912]  1000 23912     1965      320       48      272         0    53248        0             0 g++
[ 1658.610261] [  23913]  1000 23913   128727   110521   110108      413         0  1040384        0             0 cc1plus
[ 1658.610263] [  23929]  1000 23929     1965      336       48      288         0    53248        0             0 g++
[ 1658.610265] [  23930]  1000 23930   132442   106412   106090      322         0  1007616        0             0 cc1plus
[ 1658.610267] [  23937]  1000 23937     1965      336       48      288         0    53248        0             0 g++
[ 1658.610269] [  23938]  1000 23938   110974    92129    91716      413         0   888832        0             0 cc1plus
[ 1658.610271] [  23939]  1000 23939     1965      336       48      288         0    57344        0             0 g++
[ 1658.610272] [  23940]  1000 23940   100635    81733    81249      484         0   819200        0             0 cc1plus
[ 1658.610274] [  23941]  1000 23941     1965      336       48      288         0    53248        0             0 g++
[ 1658.610276] [  23942]  1000 23942   115381    91890    91589      301         0   888832        0             0 cc1plus
[ 1658.610278] [  23958]  1000 23958     1965      384       48      336         0    49152        0             0 g++
[ 1658.610280] [  23959]  1000 23959    90015    73652    73288      364         0   741376        0             0 cc1plus
[ 1658.610282] [  23979]  1000 23979     1965      336       48      288         0    49152        0             0 g++
[ 1658.610283] [  23980]  1000 23980    81828    65529    65082      447         0   675840        0             0 cc1plus
[ 1658.610285] [  23987]  1000 23987     1965      335       48      287         0    53248        0             0 g++
[ 1658.610287] [  23988]  1000 23988    84430    60385    60050      335         0   638976        0             0 cc1plus
[ 1658.610289] [  23997]  1000 23997     1965      320       48      272         0    53248        0             0 g++
[ 1658.610291] [  23998]  1000 23998    93827    70427    69971      456         0   708608        0             0 cc1plus
[ 1658.610293] [  24011]  1000 24011     1965      336       48      288         0    57344        0             0 g++
[ 1658.610295] [  24012]  1000 24012    43582    25718    25199      519         0   344064        0             0 cc1plus
[ 1658.610296] [  24021]  1000 24021     1965      336       48      288         0    53248        0             0 g++
[ 1658.610298] [  24022]  1000 24022    36455    19098    18686      412         0   286720        0             0 cc1plus
[ 1658.610300] [  24059]     0 24059      777      316        0      316         0    40960        0             0 importas
[ 1658.610302] oom-kill:constraint=CONSTRAINT_NONE,nodemask=(null),cpuset=1,mems_allowed=0,global_oom,task_memcg=/1,task=brave,pid=9999,uid=1000
[ 1658.610335] Out of memory: Killed process 9999 (brave) total-vm:55045164kB, anon-rss:21388kB, file-rss:1172kB, shmem-rss:160kB, UID:1000 pgtables:804kB oom_score_adj:200
[ 1659.774396] cc1plus invoked oom-killer: gfp_mask=0x140cca(GFP_HIGHUSER_MOVABLE|__GFP_COMP), order=0, oom_score_adj=0
[ 1659.774412] CPU: 20 UID: 1000 PID: 23594 Comm: cc1plus Tainted: P           OE       6.18.41-1-lts #1 PREEMPT(voluntary)  b1982eced5ac3ccfd79f136103dd771797e316de
[ 1659.774417] Tainted: [P]=PROPRIETARY_MODULE, [O]=OOT_MODULE, [E]=UNSIGNED_MODULE
[ 1659.774418] Hardware name: HUANANZHI /X79M-PRO
[ 1659.774420] Call Trace:
[ 1659.774422]  <TASK>
[ 1659.774424]  dump_stack_lvl+0x5d/0x80
[ 1659.774432]  dump_header+0x43/0x1aa
[ 1659.774439]  oom_kill_process.cold+0x8/0x8a
[ 1659.774442]  out_of_memory+0x231/0x570
[ 1659.774448]  __alloc_pages_slowpath.constprop.0+0xc96/0xe60
[ 1659.774454]  __alloc_frozen_pages_noprof+0x342/0x380
[ 1659.774458]  alloc_pages_mpol+0x86/0x170
[ 1659.774464]  folio_alloc_noprof+0x54/0xa0
[ 1659.774467]  __filemap_get_folio+0x1eb/0x5a0
[ 1659.774471]  ? __pfx_up_read+0x10/0x10
[ 1659.774475]  ? page_cache_ra_unbounded+0x190/0x250
[ 1659.774480]  filemap_fault+0x30b/0x17d0
[ 1659.774483]  ? filemap_map_pages+0x535/0x7e0
[ 1659.774486]  ? common_interrupt+0x13/0xa0
[ 1659.774493]  __do_fault+0x34/0x1c0
[ 1659.774497]  do_fault+0x398/0x600
[ 1659.774500]  __handle_mm_fault+0x941/0xf10
[ 1659.774503]  ? update_load_avg+0x7b/0x730
[ 1659.774509]  handle_mm_fault+0xe7/0x2b0
[ 1659.774512]  do_user_addr_fault+0x23b/0x730
[ 1659.774517]  exc_page_fault+0x8f/0x1d0
[ 1659.774521]  asm_exc_page_fault+0x26/0x30
[ 1659.774524] RIP: 0033:0x24896ae
[ 1659.774530] Code: Unable to access opcode bytes at 0x2489684.
[ 1659.774531] RSP: 002b:00007ffffc21fed0 EFLAGS: 00010202
[ 1659.774534] RAX: 000000000000002a RBX: 0000000000000000 RCX: 00007efe0d651690
[ 1659.774536] RDX: 0000000000000000 RSI: 00000000035458c0 RDI: 0000000000000011
[ 1659.774538] RBP: 000000001b8ab8f0 R08: 00007ffffc21fef0 R09: ffffffffffffffb5
[ 1659.774539] R10: 000000000000000c R11: 0000000000003b89 R12: 000000000000000c
[ 1659.774541] R13: 0000000000000009 R14: 0000000000000050 R15: 0000000000000008
[ 1659.774544]  </TASK>
[ 1659.774547] Mem-Info:
[ 1659.774557] active_anon:1681895 inactive_anon:2159964 isolated_anon:0
                active_file:0 inactive_file:1361 isolated_file:0
                unevictable:0 dirty:0 writeback:0
                slab_reclaimable:39511 slab_unreclaimable:43268
                mapped:26459 shmem:167022 pagetables:15264
                sec_pagetables:257 bounce:0
                kernel_misc_reclaimable:0
                free:51687 free_pcp:264 free_cma:0
[ 1659.774563] Node 0 active_anon:6727580kB inactive_anon:8639856kB active_file:0kB inactive_file:5444kB unevictable:0kB isolated(anon):0kB isolated(file):0kB mapped:105836kB dirty:0kB writeback:0kB shmem:668088kB shmem_thp:0kB shmem_pmdmapped:0kB anon_thp:9783296kB kernel_stack:14704kB pagetables:61056kB sec_pagetables:1028kB all_unreclaimable? no Balloon:0kB
[ 1659.774571] Node 0 DMA free:13312kB boost:0kB min:64kB low:80kB high:96kB reserved_highatomic:0KB free_highatomic:0KB active_anon:0kB inactive_anon:0kB active_file:0kB inactive_file:0kB unevictable:0kB writepending:0kB zspages:0kB present:15996kB managed:15360kB mlocked:0kB bounce:0kB free_pcp:0kB local_pcp:0kB free_cma:0kB
[ 1659.774578] lowmem_reserve[]: 0 1900 15897 15897 15897
[ 1659.774584] Node 0 DMA32 free:63704kB boost:0kB min:7004kB low:8752kB high:10500kB reserved_highatomic:0KB free_highatomic:0KB active_anon:755844kB inactive_anon:1122820kB active_file:0kB inactive_file:1684kB unevictable:0kB writepending:0kB zspages:0kB present:2015196kB managed:1946288kB mlocked:0kB bounce:0kB free_pcp:732kB local_pcp:0kB free_cma:0kB
[ 1659.774591] lowmem_reserve[]: 0 0 13997 13997 13997
[ 1659.774596] Node 0 Normal free:129504kB boost:79872kB min:140384kB low:155512kB high:170640kB reserved_highatomic:0KB free_highatomic:0KB active_anon:5971736kB inactive_anon:7517032kB active_file:0kB inactive_file:3852kB unevictable:0kB writepending:0kB zspages:0kB present:14680064kB managed:14333152kB mlocked:0kB bounce:0kB free_pcp:664kB local_pcp:0kB free_cma:0kB
[ 1659.774602] lowmem_reserve[]: 0 0 0 0 0
[ 1659.774608] Node 0 DMA: 0*4kB 0*8kB 0*16kB 0*32kB 0*64kB 0*128kB 0*256kB 0*512kB 1*1024kB (U) 2*2048kB (UM) 2*4096kB (M) = 13312kB
[ 1659.774626] Node 0 DMA32: 6*4kB (U) 195*8kB (UM) 78*16kB (UME) 22*32kB (UME) 10*64kB (UME) 18*128kB (UME) 9*256kB (ME) 10*512kB (UME) 10*1024kB (ME) 7*2048kB (UME) 6*4096kB (M) = 63056kB
[ 1659.774651] Node 0 Normal: 4330*4kB (UME) 1129*8kB (UME) 665*16kB (UME) 779*32kB (UME) 598*64kB (UME) 179*128kB (UE) 29*256kB (UE) 0*512kB 0*1024kB 0*2048kB 0*4096kB = 130528kB
[ 1659.774672] Node 0 hugepages_total=0 hugepages_free=0 hugepages_surp=0 hugepages_size=1048576kB
[ 1659.774675] Node 0 hugepages_total=0 hugepages_free=0 hugepages_surp=0 hugepages_size=2048kB
[ 1659.774677] 168685 total pagecache pages
[ 1659.774679] 0 pages in swap cache
[ 1659.774680] Free swap  = 0kB
[ 1659.774681] Total swap = 0kB
[ 1659.774682] 4177814 pages RAM
[ 1659.774684] 0 pages HighMem/MovableOnly
[ 1659.774686] 104114 pages reserved
[ 1659.774687] 0 pages cma reserved
[ 1659.774688] 0 pages hwpoisoned
[ 1659.774689] Tasks state (memory values in pages):
[ 1659.774690] [  pid  ]   uid  tgid total_vm      rss rss_anon rss_file rss_shmem pgtables_bytes swapents oom_score_adj name
[ 1659.775108] [  12565]  1000 12565     2015      252       96      156         0    65536        0             0 deps.sh
[ 1659.775110] [  23534]  1000 23534     3231     1482     1276      206         0    73728        0             0 ninja
[ 1659.775112] [  23564]  1000 23564     1965      320       48      272         0    53248        0             0 g++
[ 1659.775114] [  23570]  1000 23570     1965      336       48      288         0    53248        0             0 g++
[ 1659.775116] [  23574]  1000 23574     1965      336       48      288         0    53248        0             0 g++
[ 1659.775118] [  23576]  1000 23576     1965      336       48      288         0    49152        0             0 g++
[ 1659.775120] [  23587]  1000 23587   272645   245298   244816      482         0  2146304        0             0 cc1plus
[ 1659.775122] [  23590]  1000 23590   304697   278125   277640      485         0  2424832        0             0 cc1plus
[ 1659.775124] [  23592]  1000 23592   355631   326505   326181      324         0  2809856        0             0 cc1plus
[ 1659.775125] [  23594]  1000 23594   208640   191862   191529      333         0  1716224        0             0 cc1plus
[ 1659.775127] [  23664]  1000 23664     1965      320       48      272         0    53248        0             0 g++
[ 1659.775129] [  23665]  1000 23665   214668   198079   197785      294         0  1773568        0             0 cc1plus
[ 1659.775131] [  23720]  1000 23720     1965      336       48      288         0    53248        0             0 g++
[ 1659.775133] [  23721]  1000 23721   172998   156046   155717      329         0  1425408        0             0 cc1plus
[ 1659.775135] [  23758]  1000 23758     1965      336       48      288         0    49152        0             0 g++
[ 1659.775137] [  23759]  1000 23759   210072   184462   184136      326         0  1658880        0             0 cc1plus
[ 1659.775139] [  23761]  1000 23761     1965      320       48      272         0    53248        0             0 g++
[ 1659.775141] [  23762]  1000 23762   222471   204593   204028      565         0  1810432        0             0 cc1plus
[ 1659.775143] [  23791]  1000 23791     1965      336       48      288         0    57344        0             0 g++
[ 1659.775145] [  23792]  1000 23792   171904   154534   154017      517         0  1400832        0             0 cc1plus
[ 1659.775147] [  23808]  1000 23808     1965      336       48      288         0    57344        0             0 g++
[ 1659.775149] [  23809]  1000 23809   145568   127862   127388      474         0  1187840        0             0 cc1plus
[ 1659.775151] [  23834]  1000 23834     1965      336       48      288         0    53248        0             0 g++
[ 1659.775153] [  23835]  1000 23835   164014   147146   146774      372         0  1335296        0             0 cc1plus
[ 1659.775154] [  23842]  1000 23842     1965      320       48      272         0    61440        0             0 g++
[ 1659.775156] [  23843]  1000 23843   117429    98927    98442      485         0   954368        0             0 cc1plus
[ 1659.775158] [  23879]  1000 23879     1965      336       48      288         0    53248        0             0 g++
[ 1659.775160] [  23880]  1000 23880   118446   100122    99665      457         0   966656        0             0 cc1plus
[ 1659.775162] [  23900]  1000 23900     1965      336       48      288         0    53248        0             0 g++
[ 1659.775164] [  23901]  1000 23901   118917   100399    99956      443         0   958464        0             0 cc1plus
[ 1659.775166] [  23903]  1000 23903     1965      336       48      288         0    53248        0             0 g++
[ 1659.775168] [  23904]  1000 23904   107855    88938    88499      439         0   876544        0             0 cc1plus
[ 1659.775169] [  23912]  1000 23912     1965      320       48      272         0    53248        0             0 g++
[ 1659.775171] [  23913]  1000 23913   129239   110857   110252      605         0  1044480        0             0 cc1plus
[ 1659.775173] [  23929]  1000 23929     1965      336       48      288         0    53248        0             0 g++
[ 1659.775175] [  23930]  1000 23930   132954   106884   106282      602         0  1011712        0             0 cc1plus
[ 1659.775177] [  23937]  1000 23937     1965      336       48      288         0    53248        0             0 g++
[ 1659.775179] [  23938]  1000 23938   111038    92391    91860      531         0   888832        0             0 cc1plus
[ 1659.775181] [  23939]  1000 23939     1965      336       48      288         0    57344        0             0 g++
[ 1659.775183] [  23940]  1000 23940   100635    81730    81249      481         0   819200        0             0 cc1plus
[ 1659.775185] [  23941]  1000 23941     1965      336       48      288         0    53248        0             0 g++
[ 1659.775186] [  23942]  1000 23942   116182    92398    91925      473         0   892928        0             0 cc1plus
[ 1659.775188] [  23958]  1000 23958     1965      384       48      336         0    49152        0             0 g++
[ 1659.775190] [  23959]  1000 23959    90527    74409    73849      560         0   745472        0             0 cc1plus
[ 1659.775192] [  23979]  1000 23979     1965      336       48      288         0    49152        0             0 g++
[ 1659.775194] [  23980]  1000 23980    82340    65784    65178      606         0   679936        0             0 cc1plus
[ 1659.775196] [  23987]  1000 23987     1965      335       48      287         0    53248        0             0 g++
[ 1659.775198] [  23988]  1000 23988    84974    60881    60386      495         0   643072        0             0 cc1plus
[ 1659.775200] [  23997]  1000 23997     1965      320       48      272         0    53248        0             0 g++
[ 1659.775201] [  23998]  1000 23998    94339    70642    70067      575         0   712704        0             0 cc1plus
[ 1659.775203] [  24011]  1000 24011     1965      336       48      288         0    57344        0             0 g++
[ 1659.775205] [  24012]  1000 24012    43650    26140    25583      557         0   344064        0             0 cc1plus
[ 1659.775207] [  24021]  1000 24021     1965      336       48      288         0    53248        0             0 g++
[ 1659.775209] [  24022]  1000 24022    36455    19280    18830      450         0   290816        0             0 cc1plus
[ 1659.775211] oom-kill:constraint=CONSTRAINT_NONE,nodemask=(null),cpuset=1,mems_allowed=0,global_oom,task_memcg=/1,task=cc1plus,pid=23592,uid=1000
[ 1659.775227] Out of memory: Killed process 23592 (cc1plus) total-vm:1422524kB, anon-rss:1304724kB, file-rss:1296kB, shmem-rss:0kB, UID:1000 pgtables:2744kB oom_score_adj:0
[ 3171.140674] pcieport 0000:00:02.0: AER: Correctable error message received from 0000:03:00.0
[ 3171.140683] nvidia 0000:03:00.0: PCIe Bus Error: severity=Correctable, type=Data Link Layer, (Transmitter ID)
[ 3171.140685] nvidia 0000:03:00.0:   device [10de:1cb3] error status/mask=00001000/0000a000
[ 3171.140687] nvidia 0000:03:00.0:    [12] Timeout               
[ 4234.019831] perf: interrupt took too long (3186 > 3161), lowering kernel.perf_event_max_sample_rate to 62700
```
